package com.example.tdd

import autoparams.AutoSource
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate
import kotlin.test.assertEquals

@SpringBootTest
class ExpiryDateCalculatorTest {

    @ParameterizedTest
    @AutoSource
    @DisplayName("만원 납부하면 한달 뒤가 만료일이 됨")
    fun `when pay 10,000, then set expiry time 1 month later`() {
        assertExpiryDate(LocalDate.of(2019, 3, 1), 10_000, LocalDate.of(2019, 4, 1))
        assertExpiryDate(LocalDate.of(2019, 5, 5), 10_000, LocalDate.of(2019, 6, 5))
    }

    private fun assertExpiryDate(billingDate: LocalDate, payAmount: Int, expectedExpiryDate: LocalDate) {
        val cal = ExpiryDateCalculator()
        val realExpiryDate = cal.calculateExpiryDate(billingDate, payAmount)
        assertEquals(expectedExpiryDate, realExpiryDate)
    }
}
