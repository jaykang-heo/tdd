package com.example.tdd

import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ExpiryDateCalculator {

    fun calculateExpiryDate(date: LocalDate, payAmount: Int): LocalDate {
        return LocalDate.of(2019, 4, 1)
    }
}
