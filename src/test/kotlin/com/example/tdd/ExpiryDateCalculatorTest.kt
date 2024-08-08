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
        assertExpiryDate(PayData(LocalDate.of(2019, 3, 1), 10_000), LocalDate.of(2019, 4, 1))
        assertExpiryDate(PayData(LocalDate.of(2019, 5, 5), 10_000), LocalDate.of(2019, 6, 5))
    }

    @ParameterizedTest
    @AutoSource
    @DisplayName("납부일과 한달 뒤 일자가 같지 않음")
    fun `when pay 10,000, expiry date does not match`() {
        assertExpiryDate(PayData(LocalDate.of(2019, 1, 31), 10_000), LocalDate.of(2019, 2, 28))
        assertExpiryDate(PayData(LocalDate.of(2019, 5, 31), 10_000), LocalDate.of(2019, 6, 30))
        assertExpiryDate(PayData(LocalDate.of(2020, 1, 31), 10_000), LocalDate.of(2020, 2, 29))
    }

    private fun assertExpiryDate(payData: PayData, expectedExpiryDate: LocalDate) {
        val cal = ExpiryDateCalculator()
        val realExpiryDate = cal.calculateExpiryDate(payData)
        assertEquals(expectedExpiryDate, realExpiryDate)
    }
}
