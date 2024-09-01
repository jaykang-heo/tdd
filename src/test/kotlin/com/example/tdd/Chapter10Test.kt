package com.example.tdd

import EmailNotifier
import FakeUserRepository
import UserRegister
import net.bytebuddy.asm.Advice.Argument
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentCaptor
import org.mockito.BDDMockito
import org.mockito.BDDMockito.then
import org.mockito.Mockito
import org.mockito.Mockito.mock
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

    val answers = listOf(1, 2, 3, 4)
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
            surveyId = 1L,
            respondentId = 100L,
            answers = listOf(1, 2, 3, 4)
        )
        svc.answerSurvey(surveyAnswer)

        // 저장 결과 확인
        val savedAnswer = surveyAnswerRepository.findBySurveyAndRespondent(1L, 100L)
        assertAll(
            { assertEquals(100L, savedAnswer?.respondentId) },
            { assertEquals(4, savedAnswer?.answers?.size) },
            { assertEquals(1, savedAnswer?.answers?.get(0)) },
            { assertEquals(2, savedAnswer?.answers?.get(1)) },
            { assertEquals(3, savedAnswer?.answers?.get(2)) },
            { assertEquals(4, savedAnswer?.answers?.get(3)) }
        )
    }

    private val fakeRepository = FakeUserRepository()
    private val mockEmailNotifier = mock(EmailNotifier::class.java)
    private val userRegister = UserRegister(fakeRepository, mockEmailNotifier)


    @Test
    @DisplayName("같은 ID가 없으면 가입에 성공하고 메일을 전송함")
    fun registerAndSendMail() {
        userRegister.register("id", "pw", "email")

        // 검증 1: 회원 데이터가 올바르게 저장되었는지 검증
        val savedUser = fakeRepository.findById("id")!!
        assertEquals("id", savedUser.id)
        assertEquals("email", savedUser.email)

        // 검증 2: 이메일 발송을 요청했는지 검증
        val captor = ArgumentCaptor.forClass(String::class.java)
        BDDMockito.then(mockEmailNotifier).should().sendRegisteredEmail(captor.capture())

        val realEmail = captor.value
        assertEquals("email@email.com", realEmail)
    }

    @Test
    @DisplayName("같은 ID가 없으면 가입 성공함")
    fun noDupId_RegisterSuccess() {
        userRegister.register("id", "pw", "email")

        val savedUser = fakeRepository.findById("id")!!
        assertEquals("id", savedUser.id)
        assertEquals("email", savedUser.email)
    }

    @Test
    @DisplayName("가입하면 메일을 전송함")
    fun whenRegisterThenSendEmail() {
        userRegister.register("id", "pw", "email")

        val captor = ArgumentCaptor.forClass(String::class.java)
        then(mockEmailNotifier).should().sendRegisteredEmail(captor.capture())

        val realEmail = captor.value
        assertEquals("email@email.com", realEmail)
    }

    @Test
    @DisplayName("약한 암호면 가입 실패")
    fun weakPassword() {
        BDDMockito.given(mockPasswordChecker.checkPasswordWeak(Mockito.anyString()))
            .willReturn(true)

        assertThrows<WeakPasswordException> { userRegister.register("id", "pw", "email") }
    }
}
