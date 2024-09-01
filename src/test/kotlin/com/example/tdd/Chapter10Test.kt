import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.jdbc.Sql

@SpringBootTest
@Sql("classpath:init-data.sql")
class UserRegisterIntTestUsingSql {

    @Autowired
    private lateinit var register: UserRegister

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    @Test
    fun `동일ID가_이미_존재하면_익셉션()`() {
        // 실행, 결과 확인
        assertThrows<DupIdException> {
            register.register("cbk", "strongpw", "email@email.com")
        }
    }

    @Test
    fun `존재하지_않으면_저장함()`() {
        // 실행
        register.register("cbk2", "strongpw", "email@email.com")

        // 결과 확인
        val count = jdbcTemplate.queryForObject(
            "SELECT count(*) FROM user WHERE id = ?",
            Int::class.java,
            "cbk2"
        )
        assert(count == 1)
    }
}

// These classes are assumed to exist in your codebase
class UserRegister {
    fun register(id: String, password: String, email: String) {
        // Implementation details
    }
}

class DupIdException : RuntimeException()