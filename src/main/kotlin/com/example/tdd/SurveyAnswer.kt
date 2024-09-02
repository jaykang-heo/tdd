package com.example.tdd

data class SurveyAnswer(
    val surveyId: Long,
    val respondentId: Long,
    val answers: List<Int>
)
