package Common

import BusinessLogic.Models.APIModeler
import BusinessLogic.Models.Field
import BusinessLogic.Models.Model

class CodeGeneratorGo : CodeGenerator {
    override fun generateCode(
        apiModeler: APIModeler,
        includeComments: Boolean,
        includeDecorators: Boolean,
        layerModels: Boolean
    ): String {
        val code = StringBuilder()
        code.append("// Generated code for ${apiModeler.modelName}\n\n")
        code.append("package models\n\n")

        apiModeler.models.forEach { model ->
            if (layerModels) {
                // Generate models for each layer
                apiModeler.modelDetails.layer.forEach { layer ->
                    code.append(generateModelCode(model, includeComments, layer))
                }
            } else {
                // Generate a single model (existing behavior)
                code.append(generateModelCode(model, includeComments))
            }
        }
        return code.toString()
    }

    private fun generateModelCode(model: Model, includeComments: Boolean, layer: String? = null): String {
        val code = StringBuilder()
        if (includeComments) {
            code.append("// ${model.description}\n")
        }

        // Generate struct name with layer (if provided)
        val structName = if (layer != null) {
            "${model.name.capitalize()}${layer}Model" // Example: DogBusinessLogicModel
        } else {
            model.name.capitalize()
        }
        code.append("type $structName struct {\n")

        model.fields.forEach { field ->
            code.append(generateFieldCode(field, includeComments))
        }
        code.append("}\n\n")
        return code.toString()
    }

    private fun generateFieldCode(field: Field, includeComments: Boolean): String {
        val code = StringBuilder()
        if (includeComments && field.description.isNotEmpty()) {
            code.append("\t// ${field.description}\n")
        }
        val goType = getGoType(field.dataType)
        val goName = field.name.capitalize() // Go uses capitalized field names for public access
        code.append("\t$goName $goType `json:\"${field.name}\"`\n")
        return code.toString()
    }

    private fun getGoType(kotlinType: String): String {
        return when (kotlinType.lowercase()) {
            "string" -> "string"
            "int" -> "int"
            "boolean" -> "bool"
            "float" -> "float32"
            "double" -> "float64"
            else -> "interface{}"
        }
    }
}