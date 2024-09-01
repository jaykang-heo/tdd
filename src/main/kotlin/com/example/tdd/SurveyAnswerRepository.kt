package com.example.tdd

interface SurveyAnswerRepository {
    fun findBySurveyAndRespondent(surveyId: Long, respondentId: Long): SurveyAnswer
}