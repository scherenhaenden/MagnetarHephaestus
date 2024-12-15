package BusinessLogic.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.subclass
import kotlinx.serialization.*
import kotlinx.serialization.json.Json

import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

@Serializable
data class APIModeler(
    val author: String,
    val modelName: String,
    val createdDate: String,
    val outputLanguage: List<String>,
    val modelDetails: ModelDetails,
    val models: List<Model>
)

@Serializable
data class ModelDetails(
    val domain: String,
    val layer: Array<String> = arrayOf(),
    val direction: String
)

@Serializable
data class Model(
    val name: String,
    val description: String,
    val example: JsonElement?, // Retained JsonElement if needed for dynamic content
    val fields: List<Field>
)

@Serializable
data class ExampleClass(
    val id: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val isActive: Boolean? = null,
    val createdDate: String? = null,
    val orderID: String? = null,
    val customerID: String? = null,
    val orderDate: String? = null,
    val status: String? = null,
    val items: List<Item>? = null
)

@Serializable
data class Item(
    val productID: String,
    val quantity: Long,
    val price: Double
)

@Serializable
data class Field(
    val name: String,
    val dataType: String,
    val isPrimitive: Boolean,
    val description: String,
   // @Transient // Skip serialization/deserialization for problematic properties
    //val example: ExampleUnion, // Polymorphic ExampleUnion
    val constraints: Constraints
)

@Serializable
data class Constraints(
    val required: Boolean,
    val format: String? = null,
    val minLength: Long? = null,
    val maxLength: Long? = null,
    val enum: List<String>? = null,
    val minItems: Long? = null
)

@Serializable
sealed class ExampleUnion {
    @Serializable
    @SerialName("BoolValue")
    data class BoolValue(val value: Boolean) : ExampleUnion()

    @Serializable
    @SerialName("ItemArrayValue")
    data class ItemArrayValue(val value: List<Item>) : ExampleUnion()

    @Serializable
    @SerialName("StringValue")
    data class StringValue(val value: String) : ExampleUnion()
}

@Serializable
enum class DataType {
    @SerialName("Array")
    Array,

    @SerialName("Boolean")
    Boolean,

    @SerialName("String")
    String
}


// Define the SerializersModule for ExampleUnion
val exampleUnionSerializersModule = SerializersModule {
    polymorphic(ExampleUnion::class) {
        subclass(ExampleUnion.BoolValue::class)
        subclass(ExampleUnion.ItemArrayValue::class)
        subclass(ExampleUnion.StringValue::class)
    }
}

