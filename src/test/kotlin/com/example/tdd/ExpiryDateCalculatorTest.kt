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
        assertExpiryDate(PayData(billingDate = LocalDate.of(2019, 3, 1), payAmount = 10_000), LocalDate.of(2019, 4, 1))
        assertExpiryDate(PayData(billingDate = LocalDate.of(2019, 5, 5), payAmount = 10_000), LocalDate.of(2019, 6, 5))
    }

    @ParameterizedTest
    @AutoSource
    @DisplayName("납부일과 한달 뒤 일자가 같지 않음")
    fun `when pay 10,000, expiry date does not match`() {
        assertExpiryDate(PayData(billingDate = LocalDate.of(2019, 1, 31), payAmount = 10_000), LocalDate.of(2019, 2, 28))
        assertExpiryDate(PayData(billingDate = LocalDate.of(2019, 5, 31), payAmount = 10_000), LocalDate.of(2019, 6, 30))
        assertExpiryDate(PayData(billingDate = LocalDate.of(2020, 1, 31), payAmount = 10_000), LocalDate.of(2020, 2, 29))
    }

    @ParameterizedTest
    @AutoSource
    @DisplayName("첫 납부일과 만료일 일자가 다를때 만원 납부")
    fun `when pay date different, pay 10,000`() {
        val payData = PayData(LocalDate.of(2019, 1, 31), LocalDate.of(2019, 2, 28), 10_000)
        assertExpiryDate(payData, LocalDate.of(2019, 3, 31))
        val payData2 = PayData(LocalDate.of(2019, 1, 30), LocalDate.of(2019, 2, 28), 10_000)
        assertExpiryDate(payData2, LocalDate.of(2019, 3, 30))
        val payData3 = PayData(LocalDate.of(2019, 5, 31), LocalDate.of(2019, 6, 30), 10_000)
        assertExpiryDate(payData3, LocalDate.of(2019, 7, 31))
    }

    @ParameterizedTest
    @AutoSource
    @DisplayName("이만원 이상 납부하면 비례해서 만료일 계산")
    fun `when pay above 20,000, pay relatively`() {
        assertExpiryDate(
            PayData(
                billingDate = LocalDate.of(2019, 3, 1),
                payAmount = 20_000
            ),
            LocalDate.of(2019, 5, 1)
        )

        assertExpiryDate(
            PayData(
                billingDate = LocalDate.of(2019, 3, 1),
                payAmount = 30_000
            ),
            LocalDate.of(2019, 6, 1)
        )
    }

    @ParameterizedTest
    @AutoSource
    @DisplayName("첫 납부일과 만요일 일자가 다를때 이만원 이상 납부")
    fun `first billing date different when pay above 20,000`() {
        assertExpiryDate(
            PayData(
                firstBillingDate = LocalDate.of(2019, 1, 31),
                billingDate = LocalDate.of(2019, 2, 28),
                payAmount = 20_000
            ),
            LocalDate.of(2019, 4, 30)
        )

        assertExpiryDate(
            PayData(
                firstBillingDate = LocalDate.of(2019, 1, 31),
                billingDate = LocalDate.of(2019, 2, 28),
                payAmount = 40_000
            ),
            LocalDate.of(2019, 6, 30)
        )

        assertExpiryDate(
            PayData(
                firstBillingDate = LocalDate.of(2019, 3, 31),
                billingDate = LocalDate.of(2019, 4, 30),
                payAmount = 30_000
            ),
            LocalDate.of(2019, 7, 31)
        )
    }

    @ParameterizedTest
    @AutoSource
    @DisplayName("십만원을 납부하면 1년 제공")
    fun `when pay 100,000, give 1 year`() {
        assertExpiryDate(
            PayData(
                billingDate = LocalDate.of(2019, 1, 28),
                payAmount = 100_000
            ),
            LocalDate.of(2020, 1, 28)
        )
    }

    private fun assertExpiryDate(payData: PayData, expectedExpiryDate: LocalDate) {
        val cal = ExpiryDateCalculator()
        val realExpiryDate = cal.calculateExpiryDate(payData)
        assertEquals(expectedExpiryDate, realExpiryDate)
    }
}
