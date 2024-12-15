package Common

import BusinessLogic.Models.APIModeler
import BusinessLogic.Models.Field
import BusinessLogic.Models.Model

class CodeGeneratorJava : CodeGenerator {
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
        code.append("public class $className {\n")

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

        if (includeDecorators) {
            if (field.constraints.required) {
                code.append("    @NotNull\n")
            }
        }

        val javaType = getJavaType(field.dataType)
        code.append("    private $javaType ${field.name};\n")

        return code.toString()
    }

    private fun getJavaType(kotlinType: String): String {
        return when (kotlinType.lowercase()) {
            "string" -> "String"
            "int" -> "int"
            "float", "double" -> "double"
            "boolean" -> "boolean"
            else -> "Object"
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
        code.append("public static ${model.name}$targetLayer ModelMapper(${model.name}$sourceLayer source) {\n")
        code.append("    ${model.name}$targetLayer target = new ${model.name}$targetLayer();\n")
        model.fields.forEach { field ->
            code.append("    target.${field.name} = source.${field.name};\n")
        }
        code.append("    return target;\n")
        code.append("}\n\n")
        return code.toString()
    }
}