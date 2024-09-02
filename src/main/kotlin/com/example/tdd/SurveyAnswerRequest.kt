package com.example.tdd

data class SurveyAnswerRequest(
    val surveyId: Long,
    val respondentId: Long,
    val answers: List<Int>
)
