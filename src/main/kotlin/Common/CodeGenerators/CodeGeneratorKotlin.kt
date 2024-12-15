package Common.CodeGenerators

import BusinessLogic.Models.APIModeler
import BusinessLogic.Models.Field
import BusinessLogic.Models.Model

class CodeGeneratorKotlin : CodeGenerator {
    override fun generateCode(
        apiModeler: APIModeler,
        includeComments: Boolean,
        includeDecorators: Boolean,
        layerModels: Boolean
    ): String {
        val code = StringBuilder()
        code.append("// Generated code for ${apiModeler.modelName}\n\n")

        apiModeler.models.forEach { model ->
            if (layerModels) {
                // Generate models for each layer
                apiModeler.modelDetails.layer.forEach { layer ->
                    code.append(generateModelCode(model, includeComments, includeDecorators, layer))
                }
                // Generate mappers between layers (if there are 2 or more)
                if (apiModeler.modelDetails.layer.size >= 2) {
                    for (i in 0 until apiModeler.modelDetails.layer.size - 1) {
                        val sourceLayer = apiModeler.modelDetails.layer[i]
                        val targetLayer = apiModeler.modelDetails.layer[i + 1]
                        code.append(
                            generateMapperCode(
                                model,
                                sourceLayer,
                                targetLayer,
                                includeComments
                            )
                        )
                    }
                }
            } else {
                // Generate a single model (existing behavior)
                code.append(generateModelCode(model, includeComments, includeDecorators))
            }
        }
        return code.toString()
    }

    private fun generateModelCode(
        model: Model,
        includeComments: Boolean,
        includeDecorators: Boolean,
        layer: String? = null
    ): String {
        val code = StringBuilder()
        if (includeComments) {
            code.append("/**\n")
            code.append(" * ${model.description}\n")
            code.append(" */\n")
        }

        // Generate class name with layer (if provided)
        val className = if (layer != null) {
            "${model.name}${layer}Model" // Example: DogBusinessLogicModel
        } else {
            model.name
        }
        code.append("data class $className(\n")

        model.fields.forEachIndexed { index, field ->
            code.append(generateFieldCode(field, includeComments, index == model.fields.lastIndex))
        }
        code.append(")\n\n")
        return code.toString()
    }

    private fun generateFieldCode(field: Field, includeComments: Boolean, isLast: Boolean): String {
        val code = StringBuilder()

        if (includeComments && field.description.isNotEmpty()) {
            code.append("    /** ${field.description} */\n")
        }

        val kotlinType = getKotlinType(field.dataType)
        code.append("    val ${field.name}: $kotlinType${if (!isLast) "," else ""}\n")

        return code.toString()
    }

    private fun getKotlinType(kotlinType: String): String {
        return when (kotlinType.lowercase()) {
            "string" -> "String"
            "int" -> "Int"
            "float" -> "Float"
            "double" -> "Double"
            "boolean" -> "Boolean"
            else -> "Any"
        }
    }

    private fun generateMapperCode(
        model: Model,
        sourceLayer: String,
        targetLayer: String,
        includeComments: Boolean
    ): String {
        val code = StringBuilder()
        if (includeComments) {
            code.append("/**\n")
            code.append(" * Maps ${model.name}$sourceLayer to ${model.name}$targetLayer\n")
            code.append(" */\n")
        }
        code.append(
            "fun ${model.name}$sourceLayer.ModelMapper(): ${model.name}$targetLayer {\n"
        )
        code.append("    return ${model.name}$targetLayer(\n")
        model.fields.forEach { field ->
            code.append("        ${field.name} = this.${field.name},\n")
        }
        code.append("    )\n")
        code.append("}\n\n")
        return code.toString()
    }
}