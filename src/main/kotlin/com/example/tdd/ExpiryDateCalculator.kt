package com.example.tdd

import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ExpiryDateCalculator {

    fun calculateExpiryDate(payData: PayData): LocalDate {
        if (payData.firstBillingDate != null) {
            val candidateExp = payData.billingDate.plusMonths(1)
            if (payData.firstBillingDate.dayOfMonth != candidateExp.dayOfMonth) {
                return candidateExp.withDayOfMonth(payData.firstBillingDate.dayOfMonth)
            }
        }
        return payData.billingDate.plusMonths(1)
    }
}
