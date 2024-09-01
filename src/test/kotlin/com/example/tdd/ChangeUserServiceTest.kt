import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertTrue

// 10.11 각 테스트 메서드가 자신에 맞게 상황을 설정하는 코드
class ChangeUserServiceTest {

    private lateinit var changeService: ChangeUserService
    private lateinit var memoryRepository: MemoryRepository

    @BeforeEach
    fun setUp() {
        memoryRepository = MemoryRepository()
        changeService = ChangeUserService(memoryRepository)
    }

    @Test
    fun noUser() {
        assertThrows<UserNotFoundException> {
            changeService.changeAddress("id", Address("서울", "남부"))
        }
    }

    @Test
    fun changeAddress() {
        memoryRepository.save(
            User("id", "name", "pw", Address("서울", "남부"))
        )

        changeService.changeAddress("id", Address("경기", "남부"))

        val user = memoryRepository.findById("id")
        assertEquals("경기", user?.address?.city)
    }

    @Test
    fun changePw() {
        memoryRepository.save(
            User("id", "name", "oldpw", Address("서울", "남부"))
        )

        changeService.changePw("id", "oldpw", "newpw")

        val user = memoryRepository.findById("id")
        assertTrue(user?.matchPassword("newpw") ?: false)
    }
}

// Supporting classes and interfaces
class ChangeUserService(private val repository: MemoryRepository) {
    fun changeAddress(id: String, newAddress: Address) {
        val user = repository.findById(id) ?: throw UserNotFoundException()
        user.address = newAddress
        repository.save(user)
    }

    fun changePw(id: String, currentPw: String, newPw: String) {
        val user = repository.findById(id) ?: throw UserNotFoundException()
        if (!user.matchPassword(currentPw)) throw IdPasswordNotMatchException()
        user.changePassword(newPw)
        repository.save(user)
    }
}

class MemoryRepository {
    private val users = mutableMapOf<String, User>()

    fun save(user: User) {
        users[user.id] = user
    }

    fun findById(id: String): User? = users[id]
}

data class User(
    val id: String,
    val name: String,
    private var password: String,
    var address: Address
) {
    fun matchPassword(pw: String): Boolean = password == pw
    fun changePassword(newPw: String) {
        password = newPw
    }
}

data class Address(val city: String, val district: String)

class UserNotFoundException : RuntimeException()
class IdPasswordNotMatchException : RuntimeException()