// 실패 환경이 다르다고 실패하지 않기

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.condition.EnabledOnOs
import org.junit.jupiter.api.condition.DisabledOnOs
import org.junit.jupiter.api.condition.OS
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.assertTrue

class BulkLoaderTest {
    private val bulkFilePath = "src/test/resources/bulk.txt"

    @Test
    fun load() {
        val loader = BulkLoader()
        loader.load(bulkFilePath)
        // Add assertions here to verify the load operation
    }
}

class ExporterTest {
    @Test
    fun export() {
        val folder = System.getProperty("java.io.tmpdir")
        val exporter = Exporter(folder)
        exporter.export("file.txt")

        val path = Paths.get(folder, "file.txt")
        assertTrue(Files.exists(path))
    }
}

class OsSpecificTest {
    @Test
    @EnabledOnOs(OS.LINUX, OS.MAC)
    fun callBash() {
        // Test bash-specific operations
    }

    @Test
    @DisabledOnOs(OS.WINDOWS)
    fun changeMode() {
        // Test file permission changes (not applicable on Windows)
    }
}

// Simulated classes to make the tests work
class Exporter(private val folder: String) {
    fun export(fileName: String) {
        Files.createFile(Paths.get(folder, fileName))
    }
}

class BulkLoader {
    fun load(filePath: String) {
        // Simulate loading a bulk file
        println("Loading file from: $filePath")
    }
}