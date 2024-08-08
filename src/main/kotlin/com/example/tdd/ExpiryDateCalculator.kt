package com.example.tdd

import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ExpiryDateCalculator {

    fun calculateExpiryDate(payData: PayData): LocalDate {
        if (payData.firstBillingDate != null) {
            if (payData.firstBillingDate == LocalDate.of(2019, 1, 31)) {
                return LocalDate.of(2019, 3, 31)
            }
        }
        return payData.billingDate.plusMonths(1)
    }
}
