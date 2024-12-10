package Common

import BusinessLogic.Models.exampleUnionSerializersModule
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString

object JsonHelper {
    val json = Json {  // Make 'json' public
        // Configure Json settings here if needed (e.g., ignoreUnknownKeys = true)
        serializersModule = exampleUnionSerializersModule
        ignoreUnknownKeys = true
    }

    inline fun <reified T> serialize(obj: T): String {
        return json.encodeToString(obj)
    }

    inline fun <reified T> deserialize(jsonString: String): T {
        return json.decodeFromString(jsonString)
    }
}