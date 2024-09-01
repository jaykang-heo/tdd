import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

// 통합 테스트의 상황 설정을 위한 보조 클래스 사용하기
// 10.14 통합 테스트를 위한 상황 설정 클래스
// 10.15 상황 보조 클래스를 이용한 상황 설정
@Component
class UserGivenHelper(private val jdbcTemplate: JdbcTemplate) {
    fun givenUser(id: String, pw: String, email: String) {
        jdbcTemplate.update("""
            insert into user values (?, ?, ?) 
            on duplicate key update password = ?, email = ?
        """, id, pw, email, pw, email)
    }
}

@SpringBootTest
class UserRegistrationTest {
    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    @Autowired
    private lateinit var given: UserGivenHelper

    @Autowired
    private lateinit var register: UserRegister

    @BeforeEach
    fun setUp() {
        given = UserGivenHelper(jdbcTemplate)
    }

    @Test
    fun dupId() {
        // Given
        given.givenUser("cbk", "pw", "cbk@cbk.com")

        // When & Then
        assertThrows<DupIdException> {
            register.register("cbk", "strongpw", "email@email.com")
        }
    }
}

// These interfaces and classes are assumed to exist in your codebase
interface UserRegister {
    fun register(id: String, password: String, email: String)
}

class DupIdException : RuntimeException()

// Example implementation of UserRegister (you would need to implement this)
@Component
class UserRegisterImpl(private val jdbcTemplate: JdbcTemplate) : UserRegister {
    override fun register(id: String, password: String, email: String) {
        val count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM user WHERE id = ?",
            Int::class.java,
            id
        ) ?: 0

        if (count > 0) {
            throw DupIdException()
        }

        jdbcTemplate.update(
            "INSERT INTO user (id, password, email) VALUES (?, ?, ?)",
            id, password, email
        )
    }
}