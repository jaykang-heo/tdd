import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

enum class ItemType { ADMIN, USER }

data class Item(val type: ItemType)

class Translator {
    private val translations = mapOf("cat" to "고양이")

    fun translate(word: String): String {
        return translations[word] ?: word
    }

    fun contains(word: String): Boolean {
        return translations.containsKey(word)
    }
}

class ItemService {
    fun getItems(): List<Item> {
        return listOf(Item(ItemType.ADMIN), Item(ItemType.USER))
    }
}

class TranslatorTest {
    @Test
    fun canTranslateBasicWord() {
        val tr = Translator()
        assertTranslationOfBasicWord(tr, "cat")
    }

    private fun assertTranslationOfBasicWord(tr: Translator, word: String) {
        assertTrue(tr.contains(word))
        assertEquals("고양이", tr.translate(word))
    }
}

class ItemServiceTest {
    private val itemService = ItemService()

    @Test
    fun firstShoulBeAdminItem() {
        givenAdminItem()
        givenUserItem()

        val items = itemService.getItems()

        assertTrue(items.size > 0)
        assertEquals(ItemType.ADMIN, items[0].type)
        assertEquals(ItemType.USER, items[1].type)
    }

    private fun givenAdminItem() {
        // In a real scenario, this would add an admin item to the service
    }

    private fun givenUserItem() {
        // In a real scenario, this would add a user item to the service
    }
}

fun main() {
    val translatorTest = TranslatorTest()
    translatorTest.canTranslateBasicWord()

    val itemServiceTest = ItemServiceTest()
    itemServiceTest.firstShoulBeAdminItem()

    println("All tests passed!")
}