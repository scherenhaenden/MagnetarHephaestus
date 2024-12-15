package Common.CodeGenerators

import BusinessLogic.Models.APIModeler
import BusinessLogic.Models.Field
import BusinessLogic.Models.Model

class CodeGeneratorTypeScript : CodeGenerator {
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
        code.append("export class $className {\n")

        model.fields.forEach { field ->
            code.append(generateFieldCode(field, includeComments, includeDecorators))
        }
        code.append("}\n\n")
        return code.toString()
    }

    private fun generateFieldCode(
        field: Field,
        includeComments: Boolean,
        includeDecorators: Boolean
    ): String {
        val code = StringBuilder()

        if (includeComments && field.description.isNotEmpty()) {
            code.append("    /**\n")
            code.append("     * ${field.description}\n")
            code.append("     */\n")
        }

        val tsType = getTypeScriptType(field.dataType)
        code.append("    public ${field.name}: $tsType;\n")

        return code.toString()
    }

    private fun getTypeScriptType(kotlinType: String): String {
        return when (kotlinType.lowercase()) {
            "string" -> "string"
            "int", "float", "double" -> "number"
            "boolean" -> "boolean"
            else -> "any"
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
            "export const map${model.name}${sourceLayer}To${model.name}${targetLayer} = (source: ${model.name}$sourceLayer): ${model.name}$targetLayer => ({\n"
        )
        code.append("    return {\n")
        model.fields.forEach { field ->
            code.append("        ${field.name}: source.${field.name},\n")
        }
        code.append("    };\n")
        code.append("});\n\n")
        return code.toString()
    }
}