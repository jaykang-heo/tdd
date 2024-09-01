package com.example.tdd

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.junit.jupiter.api.Assertions.*

// 셋업을 이용해서 중복된 상황을 설정하지 않기
// 10.10 상황 관련 코드의 중복을 제거한 예
@SpringBootTest
class ChangeUserServiceTest {

    @Autowired
    lateinit var memoryRepository: MemoryRepository

    @Autowired
    lateinit var changeService: ChangeUserService

    @Test
    fun setUp() {
        changeService = ChangeUserService(memoryRepository)
        memoryRepository.save(
            User("id", "name", "pw", Address("서울", "남부"))
        )
    }

    @Test
    fun noUser() {
        assertThrows<UserNotFoundException> {
            changeService.changeAddress("id2", Address("서울", "남부"))
        }
    }

    @Test
    fun changeAddress() {
        changeService.changeAddress("id", Address("서울", "남부"))
        val user = memoryRepository.findById("id")
        assertEquals("서울", user?.address?.city)
    }

    @Test
    fun changePw() {
        changeService.changePw("id", "pw", "newpw")
        val user = memoryRepository.findById("id")
        assertTrue(user?.matchPassword("newpw") ?: false)
    }

    @Test
    fun pwNotMatch() {
        assertThrows<IdPwNotMatchException> {
            changeService.changePw("id", "pw2", "newpw")
        }
    }
}

// Required data classes and interfaces
data class User(
    val id: String,
    val name: String,
    private var password: String,
    var address: Address
) {
    fun matchPassword(pw: String): Boolean = this.password == pw
}

data class Address(val city: String, val district: String)

interface MemoryRepository {
    fun save(user: User)
    fun findById(id: String): User?
}

class ChangeUserService(private val repository: MemoryRepository) {
    fun changeAddress(id: String, newAddress: Address) {
        val user = repository.findById(id) ?: throw UserNotFoundException()
        user.address = newAddress
        repository.save(user)
    }

    fun changePw(id: String, currentPw: String, newPw: String) {
        val user = repository.findById(id) ?: throw UserNotFoundException()
        if (!user.matchPassword(currentPw)) throw IdPwNotMatchException()
        // Assuming there's a method to change password in User class
        // user.changePassword(newPw)
        repository.save(user)
    }
}

class UserNotFoundException : RuntimeException()
class IdPwNotMatchException : RuntimeException()