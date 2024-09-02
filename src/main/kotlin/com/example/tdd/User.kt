data class User(val id: String, val password: String, val email: String)

interface UserRepository {
    fun save(user: User)
    fun findById(id: String): User?
}

class FakeUserRepository : UserRepository {
    private val users = mutableMapOf<String, User>()

    override fun save(user: User) {
        users[user.id] = user
    }

    override fun findById(id: String): User? = users[id]
}

interface EmailNotifier {
    fun sendRegisteredEmail(email: String)
}

class UserRegister(
    private val userRepository: UserRepository,
    private val emailNotifier: EmailNotifier
) {
    fun register(id: String, password: String, email: String) {
        val user = User(id, password, email)
        userRepository.save(user)
        emailNotifier.sendRegisteredEmail(email)  // Make sure this line is present
    }
}