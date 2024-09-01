import java.time.LocalDateTime

enum class SurveyStatus { READY, OPEN }

data class Item(val number: Int, val text: String)

data class Question(val number: Int, val text: String, val items: List<Item>)

data class Survey(
    val id: Long,
    val title: String,
    val status: SurveyStatus,
    val endOfPeriod: LocalDateTime,
    val questions: List<Question>
)

class SurveyBuilder {
    private var id: Long = 0
    private var title: String = ""
    private var status: SurveyStatus = SurveyStatus.READY
    private var endOfPeriod: LocalDateTime = LocalDateTime.now().plusDays(5)
    private var questions: MutableList<Question> = mutableListOf()

    fun id(id: Long) = apply { this.id = id }
    fun title(title: String) = apply { this.title = title }
    fun status(status: SurveyStatus) = apply { this.status = status }
    fun endOfPeriod(endOfPeriod: LocalDateTime) = apply { this.endOfPeriod = endOfPeriod }
    fun questions(questions: List<Question>) = apply { this.questions = questions.toMutableList() }

    fun open() = apply { this.status = SurveyStatus.OPEN }

    fun build() = Survey(id, title, status, endOfPeriod, questions)
}

object TestSurveyFactory {
    fun createAnswerableSurvey(id: Long): Survey {
        return SurveyBuilder()
            .id(id)
            .status(SurveyStatus.OPEN)
            .endOfPeriod(LocalDateTime.now().plusDays(5))
            .questions(listOf(
                Question(1, "질문1", listOf(Item(1, "보기1"), Item(2, "보기2"))),
                Question(2, "질문2", listOf(Item(1, "답1"), Item(2, "답2")))
            ))
            .build()
    }
}

class TestSurveyBuilder {
    private val builder = SurveyBuilder()

    init {
        builder.id(1L)
            .title("제목")
            .endOfPeriod(LocalDateTime.now().plusDays(5))
            .questions(listOf(
                Question(1, "질문1", listOf(Item(1, "보기1"), Item(2, "보기2"))),
                Question(2, "질문2", listOf(Item(1, "답1"), Item(2, "답2")))
            ))
    }

    fun id(id: Long) = apply { builder.id(id) }
    fun title(title: String) = apply { builder.title(title) }
    fun open() = apply { builder.open() }
    fun build() = builder.build()
}

// Test setup
class MemorySurveyRepository {
    private val surveys = mutableMapOf<Long, Survey>()

    fun save(survey: Survey) {
        surveys[survey.id] = survey
    }

    fun findById(id: Long): Survey? = surveys[id]
}

class SurveyService(private val repository: MemorySurveyRepository) {
    fun answer(surveyId: Long, answers: List<Int>) {
        // Implementation omitted for brevity
    }
}

// Test code
class SurveyTest {
    private val memoryRepository = MemorySurveyRepository()
    private val answerService = SurveyService(memoryRepository)

    fun testAnswer() {
        // Setup
        val survey = TestSurveyFactory.createAnswerableSurvey(1L)
        memoryRepository.save(survey)

        // Execute
        answerService.answer(1L, listOf(1, 2))

        // Assert
        // Add your assertions here
    }

    fun testCreateSurvey() {
        val survey = TestSurveyBuilder()
            .id(10L)
            .title("새로운 제목")
            .open()
            .build()

        memoryRepository.save(survey)

        // Assert
        // Add your assertions here
    }
}

fun main() {
    val test = SurveyTest()
    test.testAnswer()
    test.testCreateSurvey()
    println("Tests completed")
}