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
        // given
        val billingDate = LocalDate.of(2019, 3, 1)
        val payAmount = 10_000
        val cal = ExpiryDateCalculator()

        // when
        val expiryDate = cal.calculateExpiryDate(billingDate, payAmount)

        // then
        assertEquals(LocalDate.of(2019, 4, 1), expiryDate)
    }
}
