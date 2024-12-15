package Common.CodeGenerators

import BusinessLogic.Models.APIModeler
import BusinessLogic.Models.Field
import BusinessLogic.Models.Model

class CodeGeneratorPython : CodeGenerator {
    override fun generateCode(
        apiModeler: APIModeler,
        includeComments: Boolean,
        includeDecorators: Boolean,
        layerModels: Boolean
    ): String {
        val code = StringBuilder()
        code.append("# Generated code for ${apiModeler.modelName}\n\n")

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

        // Generate class name with layer (if provided)
        val className = if (layer != null) {
            "${model.name}${layer}Model" // Example: DogBusinessLogicModel
        } else {
            model.name
        }

        if (includeComments) {
            code.append("class $className:\n")
            code.append("    \"\"\"\n")
            code.append("    ${model.description}\n")
            code.append("    \"\"\"\n")
        } else {
            code.append("class $className:\n")
        }

        model.fields.forEach { field ->
            code.append(generateFieldCode(field, includeComments))
        }
        if (model.fields.isEmpty()) {
            code.append("    pass\n")
        }
        code.append("\n")
        return code.toString()
    }

    private fun generateFieldCode(field: Field, includeComments: Boolean): String {
        val code = StringBuilder()

        if (includeComments && field.description.isNotEmpty()) {
            code.append("    # ${field.description}\n")
        }

        val pythonType = getPythonType(field.dataType)
        code.append("    ${field.name}: $pythonType\n")

        return code.toString()
    }

    private fun getPythonType(kotlinType: String): String {
        return when (kotlinType.lowercase()) {
            "string" -> "str"
            "int" -> "int"
            "float", "double" -> "float"
            "boolean" -> "bool"
            else -> "Any"
        }
    }
}