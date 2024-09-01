package com.example.tdd

import autoparams.AutoSource
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import java.time.LocalDate
import kotlin.test.assertEquals

class Chapter10Test {

    @Test
    fun dateFormat() {
        val date = LocalDate.of(1945, 8, 15)
        val dateStr = formatDate(date)
        val expected = date.year.toString() + "년" + date.month + "월"+ date.dayOfMonth + "일"
        assertEquals(expected, dateStr)
    }

    fun formatDate(date: LocalDate): String {
        return "${date.year}년${date.monthValue.toString().padStart(2, '0')}월${date.dayOfMonth.toString().padStart(2, '0')}일"
    }
}