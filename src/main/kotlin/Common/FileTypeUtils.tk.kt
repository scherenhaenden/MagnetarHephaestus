import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import org.yaml.snakeyaml.Yaml

object FileTypeUtils { // Define the functions within an object

    fun determineFileType(input: String): String {
        return when {
            isJson(input) -> "JSON"
            isYaml(input) -> "YAML"
            isXml(input) -> "XML"
            else -> "Unknown"
        }
    }

    fun isJson(input: String): Boolean {
        return try {
            Json.parseToJsonElement(input).jsonObject
            true
        } catch (e: Exception) {
            false
        }
    }

    fun isYaml(input: String): Boolean {
        return try {
            val yaml = Yaml()
            yaml.load<Any>(input)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun isXml(input: String): Boolean {
        return try {
            javax.xml.parsers.DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input.byteInputStream())
            true
        } catch (e: Exception) {
            false
        }
    }
}