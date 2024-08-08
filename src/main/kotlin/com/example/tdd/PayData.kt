package com.example.tdd

import java.time.LocalDate

data class PayData(
    val firstBillingDate: LocalDate? = null,
    val billingDate: LocalDate,
    val payAmount: Int
)
