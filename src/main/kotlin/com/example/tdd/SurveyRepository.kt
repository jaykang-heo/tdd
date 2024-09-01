package com.example.tdd

interface SurveyRepository {
    fun save(survey: Survey): Survey
}
