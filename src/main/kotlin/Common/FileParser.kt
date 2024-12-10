package BusinessLogic.Services

import BusinessLogic.Models.BusinessModel
import Common.FileTypeUtils

import kotlinx.serialization.json.Json
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.DumperOptions
import javax.xml.parsers.DocumentBuilderFactory
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList


object FileParser {

    fun parseToModel(input: String): BusinessModel {
        val fileType = FileTypeUtils.determineFileType(input)
        val jsonInput = when (fileType) {
            "JSON" -> input
            "YAML" -> yamlToJson(input)
            "XML" -> xmlToJson(input)
            else -> throw IllegalArgumentException("Unsupported file type: $fileType")
        }
        return parseJson(jsonInput)
    }

    private fun parseJson(input: String): BusinessModel {
        return try {
            Json.decodeFromString(BusinessModel.serializer(), input)
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid JSON structure", e)
        }
    }

    private fun yamlToJson(input: String): String {
        return try {
            val yaml = Yaml()
            val map = yaml.load<Map<String, Any>>(input)
            Json.encodeToString(map)
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid YAML structure", e)
        }
    }

    private fun xmlToJson(input: String): String {
        return try {
            val document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input.byteInputStream())
            val root = document.documentElement
            val map = elementToMap(root)
            Json.encodeToString(map)
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid XML structure", e)
        }
    }

    private fun elementToMap(element: Element): Map<String, Any> {
        val map = mutableMapOf<String, Any>()

        // Add attributes
        val attributes = element.attributes
        for (i in 0 until attributes.length) {
            val attr = attributes.item(i)
            map[attr.nodeName] = attr.nodeValue
        }

        // Add child nodes
        val children = element.childNodes
        for (i in 0 until children.length) {
            val node = children.item(i)
            if (node is Element) {
                map[node.nodeName] = elementToMap(node)
            } else if (node.nodeType == Node.TEXT_NODE && node.textContent.trim().isNotEmpty()) {
                map[element.nodeName] = node.textContent.trim()
            }
        }

        return map
    }
}
