package com.example.tdd

import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.YearMonth

@Service
class ExpiryDateCalculator {

    fun calculateExpiryDate(payData: PayData): LocalDate {
        val addedMonths = payData.payAmount / 10_000L
        if (payData.firstBillingDate != null) {
            return expiryDateUsingFirstBillingDate(payData, addedMonths)
        } else {
            return payData.billingDate.plusMonths(addedMonths)
        }
    }

    private fun expiryDateUsingFirstBillingDate(payData: PayData, addedMonths: Long): LocalDate {
        val candidateExp = payData.billingDate.plusMonths(addedMonths)
        val dayOfFirstBilling = payData.firstBillingDate!!.dayOfMonth
        if (dayOfFirstBilling != candidateExp.dayOfMonth) {
            val dayLenOfCandiMon = YearMonth.from(candidateExp).lengthOfMonth()
            if (dayLenOfCandiMon < dayOfFirstBilling) {
                return candidateExp.withDayOfMonth(dayLenOfCandiMon)
            }
            return candidateExp.withDayOfMonth(dayOfFirstBilling)
        } else {
            return candidateExp
        }
    }
}
