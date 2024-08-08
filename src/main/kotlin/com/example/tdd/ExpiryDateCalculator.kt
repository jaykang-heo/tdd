package com.example.tdd

import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.YearMonth

@Service
class ExpiryDateCalculator {

    fun calculateExpiryDate(payData: PayData): LocalDate {
        val addedMonths = payData.payAmount / 10_000L
        if (payData.firstBillingDate != null) {
            val candidateExp = payData.billingDate.plusMonths(addedMonths)
            if (payData.firstBillingDate.dayOfMonth != candidateExp.dayOfMonth) {
                if (YearMonth.from(candidateExp).lengthOfMonth() < payData.firstBillingDate.dayOfMonth) {
                    return candidateExp.withDayOfMonth(YearMonth.from(candidateExp).lengthOfMonth())
                }
                return candidateExp.withDayOfMonth(payData.firstBillingDate.dayOfMonth)
            }
        }
        return payData.billingDate.plusMonths(addedMonths)
    }
}
