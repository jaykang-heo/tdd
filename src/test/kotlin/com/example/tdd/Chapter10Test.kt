import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import java.time.LocalDateTime

// 실행 시점이 다르다고 실패하지 않기

class Member private constructor(private val expiryDate: LocalDateTime) {
    fun isExpired(): Boolean {
        return expiryDate.isBefore(LocalDateTime.now())
    }

    fun passedExpiryDate(time: LocalDateTime): Boolean {
        return expiryDate.isBefore(time)
    }

    companion object {
        fun builder() = Builder()
    }

    class Builder {
        private var expiryDate: LocalDateTime? = null

        fun expiryDate(date: LocalDateTime) = apply { this.expiryDate = date }
        fun build() = expiryDate?.let { Member(it) } ?: throw IllegalStateException("Expiry date must be set")
    }
}

class MemberTest {
    @Test
    fun notExpired() {
        val expiry = LocalDateTime.of(2019, 12, 31, 0, 0, 0)
        val m = Member.builder().expiryDate(expiry).build()
        assertFalse(m.isExpired())
    }

    @Test
    fun notExpiredFarFuture() {
        val expiry = LocalDateTime.of(2099, 12, 31, 0, 0, 0)
        val m = Member.builder().expiryDate(expiry).build()
        assertFalse(m.isExpired())
    }

    @Test
    fun notExpiredPassedExpiryDate() {
        val expiry = LocalDateTime.of(2019, 12, 31, 0, 0, 0)
        val m = Member.builder().expiryDate(expiry).build()
        assertFalse(m.passedExpiryDate(LocalDateTime.of(2019, 12, 30, 0, 0, 0)))
    }

    @Test
    fun expired_Only_1ms() {
        val expiry = LocalDateTime.of(2019, 12, 31, 0, 0, 0)
        val m = Member.builder().expiryDate(expiry).build()
        assertTrue(m.passedExpiryDate(LocalDateTime.of(2019, 12, 31, 0, 0, 0, 1000000)))
    }
}