import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.jdbc.Sql

@SpringBootTest
@Sql("classpath:init-data.sql")
class UserRegisterDuplicateTest {

    /**
     * 이 방식은 편리하지만 셋업 메서드를 이용한 상황 설정과 마찬가지로 초기화를 위한 쿼리 파일을 조금만 변경해도 많은 테스트가 꺠질 수 있다
     * 테스트가 깨지면 쿼리 파일도 같이 다시 검토해야 한다
     * 테스트 코드의 유지 보수를 귀찮고 어렵게 만든다
     *
     * 결론: 특정 테스트에서만 생성해서 테스트 코드가 완전한 하나가 되도록 해야 한다
     */


    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    @Autowired
    private lateinit var register: UserRegister

    @BeforeEach
    fun setUp() {
        jdbcTemplate.update("""
            insert into user values ('cbk', 'pw', 'cbk@cbk.com');
            insert into user values ('rddhi', 'pw1', 'rddhi@iloveadd.com');
        """)
    }

    @Test
    fun dupId() {
        // 상황
        jdbcTemplate.update("""
            insert into user values (?,?,?) 
            on duplicate key update password = ?, email = ?
        """, "cbk", "pw", "cbk@cbk.com", "pw", "cbk@cbk.com")

        // 실행, 결과 확인
        assertThrows<DupIdException> {
            register.register("cbk", "strongpw", "email@email.com")
        }
    }
}

// These classes and interfaces are assumed to exist in your codebase
interface UserRegister {
    fun register(id: String, password: String, email: String)
}

class DupIdException : RuntimeException()