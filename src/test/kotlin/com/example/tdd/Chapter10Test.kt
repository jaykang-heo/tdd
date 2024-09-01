import org.junit.jupiter.api.Test
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import java.time.LocalDateTime

class BizClock {
    companion object {
        private var instance: BizClock = DEFAULT

        fun now(): LocalDateTime = instance.timeNow()

        fun setInstance(bizClock: BizClock) {
            instance = bizClock
        }

        private val DEFAULT = object : BizClock {
            override fun timeNow() = LocalDateTime.now()
        }
    }

    open fun timeNow(): LocalDateTime = LocalDateTime.now()
}

class Member private constructor(private val expiryDate: LocalDateTime) {
    fun isExpired(): Boolean {
        return expiryDate.isBefore(BizClock.now())
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

class TestBizClock : BizClock() {
    private var now: LocalDateTime? = null

    fun setNow(now: LocalDateTime) {
        this.now = now
    }

    override fun timeNow(): LocalDateTime {
        return now ?: super.timeNow()
    }

    fun reset() {
        now = null
    }
}

class MemberTest {
    private val testClock = TestBizClock()

    @AfterEach
    fun resetClock() {
        testClock.reset()
        BizClock.setInstance(BizClock.DEFAULT)
    }

    @Test
    fun notExpired() {
        BizClock.setInstance(testClock)
        testClock.setNow(LocalDateTime.of(2019, 1, 1, 13, 0, 0))
        val expiry = LocalDateTime.of(2019, 12, 31, 0, 0, 0)
        val m = Member.builder().expiryDate(expiry).build()
        assertFalse(m.isExpired())
    }

    @Test
    fun expired() {
        BizClock.setInstance(testClock)
        testClock.setNow(LocalDateTime.of(2020, 1, 1, 13, 0, 0))
        val expiry = LocalDateTime.of(2019, 12, 31, 0, 0, 0)
        val m = Member.builder().expiryDate(expiry).build()
        assertTrue(m.isExpired())
    }

    @Test
    fun expiredByOneMillisecond() {
        BizClock.setInstance(testClock)
        testClock.setNow(LocalDateTime.of(2019, 12, 31, 0, 0, 0, 1000000))
        val expiry = LocalDateTime.of(2019, 12, 31, 0, 0, 0)
        val m = Member.builder().expiryDate(expiry).build()
        assertTrue(m.isExpired())
    }
}