package Common

import BusinessLogic.Models.APIModeler
import BusinessLogic.Models.Model
import BusinessLogic.Models.Field

interface CodeGenerator {
    fun generateCode(apiModeler: APIModeler, includeComments: Boolean = true, includeDecorators: Boolean = true): String
}


class CodeGeneratorCSharp : CodeGenerator {
    override fun generateCode(apiModeler: APIModeler, includeComments: Boolean, includeDecorators: Boolean): String {
        val code = StringBuilder()
        code.append("// Generated code for ${apiModeler.modelName}\n\n")
        apiModeler.models.forEach { model ->
            code.append(generateModelCode(model, includeComments, includeDecorators))
        }
        return code.toString()
    }

    private fun generateModelCode(model: Model, includeComments: Boolean, includeDecorators: Boolean): String {
        val code = StringBuilder()
        if (includeComments) {
            code.append("/// <summary>\n")
            code.append("/// ${model.description}\n")
            code.append("/// </summary>\n")
        }
        code.append("public class ${model.name} {\n")
        model.fields.forEach { field ->
            code.append(generateFieldCode(field, includeComments, includeDecorators))
        }
        code.append("}\n\n")
        return code.toString()
    }

    private fun generateFieldCode(field: Field, includeComments: Boolean, includeDecorators: Boolean): String {
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
}


class CodeGeneratorTypeScript : CodeGenerator {
    override fun generateCode(apiModeler: APIModeler, includeComments: Boolean, includeDecorators: Boolean): String {
        val code = StringBuilder()
        code.append("// Generated code for ${apiModeler.modelName}\n\n")
        apiModeler.models.forEach { model ->
            code.append(generateModelCode(model, includeComments, includeDecorators))
        }
        return code.toString()
    }

    private fun generateModelCode(model: Model, includeComments: Boolean, includeDecorators: Boolean): String {
        val code = StringBuilder()
        if (includeComments) {
            code.append("/**\n")
            code.append(" * ${model.description}\n")
            code.append(" */\n")
        }
        code.append("export class ${model.name} {\n")
        model.fields.forEach { field ->
            code.append(generateFieldCode(field, includeComments, includeDecorators))
        }
        code.append("}\n\n")
        return code.toString()
    }

    private fun generateFieldCode(field: Field, includeComments: Boolean, includeDecorators: Boolean): String {
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
}



class CodeGeneratorKotlin : CodeGenerator {
    override fun generateCode(apiModeler: APIModeler, includeComments: Boolean, includeDecorators: Boolean): String {
        val code = StringBuilder()
        code.append("// Generated code for ${apiModeler.modelName}\n\n")
        apiModeler.models.forEach { model ->
            code.append(generateModelCode(model, includeComments, includeDecorators))
        }
        return code.toString()
    }

    private fun generateModelCode(model: Model, includeComments: Boolean, includeDecorators: Boolean): String {
        val code = StringBuilder()
        if (includeComments) {
            code.append("/**\n")
            code.append(" * ${model.description}\n")
            code.append(" */\n")
        }
        code.append("data class ${model.name}(\n")
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
}

class CodeGeneratorPython : CodeGenerator {
    override fun generateCode(apiModeler: APIModeler, includeComments: Boolean, includeDecorators: Boolean): String {
        val code = StringBuilder()
        code.append("# Generated code for ${apiModeler.modelName}\n\n")
        apiModeler.models.forEach { model ->
            code.append(generateModelCode(model, includeComments))
        }
        return code.toString()
    }

    private fun generateModelCode(model: Model, includeComments: Boolean): String {
        val code = StringBuilder()
        if (includeComments) {
            code.append("class ${model.name}:\n")
            code.append("    \"\"\"\n")
            code.append("    ${model.description}\n")
            code.append("    \"\"\"\n")
        } else {
            code.append("class ${model.name}:\n")
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


class CodeGeneratorJava : CodeGenerator {
    override fun generateCode(apiModeler: APIModeler, includeComments: Boolean, includeDecorators: Boolean): String {
        val code = StringBuilder()
        code.append("// Generated code for ${apiModeler.modelName}\n\n")
        apiModeler.models.forEach { model ->
            code.append(generateModelCode(model, includeComments, includeDecorators))
        }
        return code.toString()
    }

    private fun generateModelCode(model: Model, includeComments: Boolean, includeDecorators: Boolean): String {
        val code = StringBuilder()
        if (includeComments) {
            code.append("/**\n")
            code.append(" * ${model.description}\n")
            code.append(" */\n")
        }
        code.append("public class ${model.name} {\n")
        model.fields.forEach { field ->
            code.append(generateFieldCode(field, includeComments, includeDecorators))
        }
        code.append("}\n\n")
        return code.toString()
    }

    private fun generateFieldCode(field: Field, includeComments: Boolean, includeDecorators: Boolean): String {
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
}


