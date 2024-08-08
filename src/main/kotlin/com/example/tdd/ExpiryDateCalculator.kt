package com.example.tdd

import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ExpiryDateCalculator {

    fun calculateExpiryDate(billingDate: LocalDate, payAmount: Int): LocalDate {
        return billingDate.plusMonths(1)
    }
}
