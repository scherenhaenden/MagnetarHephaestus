package Common

import BusinessLogic.Models.APIModeler
import BusinessLogic.Models.Field
import BusinessLogic.Models.Model

class CodeGeneratorCSharp : CodeGenerator {
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
                // Generate mappers between layers (if there are 2 or more layers)
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
            code.append("/// <summary>\n")
            code.append("/// ${model.description}\n")
            code.append("/// </summary>\n")
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
            code.append("    /// <summary>\n")
            code.append("    /// ${field.description}\n")
            code.append("    /// </summary>\n")
        }

        if (includeDecorators) {
            if (field.constraints.required) {
                code.append("    [Required]\n")
            }
            field.constraints.format?.let {
                code.append("    [RegularExpression(\"${getCSharpRegex(it)}\")]\n")
            }
            field.constraints.minLength?.let {
                code.append("    [MinLength($it)]\n")
            }
            field.constraints.maxLength?.let {
                code.append("    [MaxLength($it)]\n")
            }
        }

        val csharpType = getCSharpType(field.dataType)
        code.append("    public $csharpType ${field.name.capitalize()} { get; set; }\n")

        return code.toString()
    }

    private fun getCSharpType(kotlinType: String): String {
        return when (kotlinType.lowercase()) {
            "string" -> "string"
            "int" -> "int"
            "boolean" -> "bool"
            "float" -> "float"
            "double" -> "double"
            else -> kotlinType
        }
    }

    private fun getCSharpRegex(format: String): String {
        return when (format) {
            "uuid" -> "[a-fA-F0-9\\-]{36}"
            "email" -> "^[^@]+@[^@]+\\.[^@]+$"
            else -> ".*"
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
            code.append("/// <summary>\n")
            code.append("/// Maps ${model.name}$sourceLayer to ${model.name}$targetLayer\n")
            code.append("/// </summary>\n")
        }
        code.append(
            "public static ${model.name}$targetLayer ModelMapper(this ${model.name}$sourceLayer source) {\n"
        )
        code.append("    return new ${model.name}$targetLayer {\n")
        model.fields.forEach { field ->
            code.append("        ${field.name.capitalize()} = source.${field.name.capitalize()},\n")
        }
        code.append("    };\n")
        code.append("}\n\n")
        return code.toString()
    }
}