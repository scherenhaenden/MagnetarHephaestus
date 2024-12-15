package Common.CodeGenerators

import BusinessLogic.Models.APIModeler
import BusinessLogic.Models.Field
import BusinessLogic.Models.Model

class CodeGeneratorCpp : CodeGenerator {
    override fun generateCode(
        apiModeler: APIModeler,
        includeComments: Boolean,
        includeDecorators: Boolean,
        layerModels: Boolean
    ): String {
        val code = StringBuilder()
        code.append("// Generated code for ${apiModeler.modelName}\n\n")
        code.append("#include <string>\n")
        code.append("#include <vector>\n\n")

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
            code.append("/*\n")
            code.append(" * ${model.description}\n")
            code.append(" */\n")
        }

        // Generate class name with layer (if provided)
        val className = if (layer != null) {
            "${model.name}${layer}Model" // Example: DogBusinessLogicModel
        } else {
            model.name
        }
        code.append("class $className {\n")
        code.append("public:\n")

        model.fields.forEach { field ->
            code.append(generateFieldCode(field, includeComments))
        }
        code.append("};\n\n")
        return code.toString()
    }


    private fun generateFieldCode(field: Field, includeComments: Boolean): String {
        val code = StringBuilder()
        if (includeComments && field.description.isNotEmpty()) {
            code.append("\t/*\n")
            code.append("\t * ${field.description}\n")
            code.append("\t */\n")
        }
        val cppType = getCppType(field.dataType)
        code.append("\t$cppType ${field.name};\n")
        return code.toString()
    }

    private fun getCppType(kotlinType: String): String {
        return when (kotlinType.lowercase()) {
            "string" -> "std::string"
            "int" -> "int"
            "boolean" -> "bool"
            "float" -> "float"
            "double" -> "double"
            else -> "auto" // Or a more specific type if possible
        }
    }
}