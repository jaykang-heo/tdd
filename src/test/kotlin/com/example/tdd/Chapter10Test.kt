package com.example.tdd

import autoparams.AutoSource
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import java.time.LocalDate
import kotlin.test.assertEquals

class Chapter10Test {

    @Test
    fun dateFormat() {
        val date = LocalDate.of(1945, 8, 15)
        val dateStr = formatDate(date)
        val expected = "1945년8월15일"
        assertEquals(expected, dateStr)
    }

    fun formatDate(date: LocalDate): String {
        return "${date.year}년${date.monthValue.toString().padStart(2, '0')}월${date.dayOfMonth.toString().padStart(2, '0')}일"
    }

    val answers = listOf(1,2,3,4)
    val respondentId = 100L
    private val surveyRepository = object : SurveyRepository {
        override fun save(survey: Survey) = survey
    }

    private val surveyAnswerRepository = object : SurveyAnswerRepository {
        override fun findBySurveyAndRespondent(surveyId: Long, respondentId: Long) =
            SurveyAnswer(surveyId, respondentId, listOf(1, 2, 3, 4))
    }
    private val svc = SurveyService(surveyRepository, surveyAnswerRepository)

    @Test
    @DisplayName("답변에 성공하면 결과 저장함")
    fun saveAnswerSuccessfully() {
        // 답변할 설문이 존재
        val survey = SurveyFactory.createApprovedSurvey(1L)
        surveyRepository.save(survey)

        // 설문 답변
        val surveyAnswer = SurveyAnswerRequest(
            surveyId = survey.id,
            respondentId = respondentId,
            answers = answers
        )
        svc.answerSurvey(surveyAnswer)

        // 저장 결과 확인
        val savedAnswer = surveyAnswerRepository.findBySurveyAndRespondent(survey.id, respondentId)
        assertAll(
            { assertEquals(respondentId, savedAnswer?.respondentId) },
            { assertEquals(answers.size, savedAnswer?.answers?.size) },
            { assertEquals(answers[0], savedAnswer?.answers?.get(0)) },
            { assertEquals(answers[1], savedAnswer?.answers?.get(1)) },
            { assertEquals(answers[2], savedAnswer?.answers?.get(2)) },
            { assertEquals(answers[2], savedAnswer?.answers?.get(3)) }
        )
    }
}