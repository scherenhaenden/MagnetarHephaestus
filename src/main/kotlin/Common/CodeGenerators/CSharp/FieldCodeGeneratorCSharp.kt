package Common.CodeGenerators.CSharp

import BusinessLogic.Models.Field

class FieldCodeGeneratorCSharp {
    fun generateFieldCode(
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
            field.constraints.required?.let { code.append("    [Required]\n") }
            field.constraints.format?.let { code.append("    [RegularExpression(\"${getCSharpRegex(it)}\")]\n") }
            field.constraints.minLength?.let { code.append("    [MinLength($it)]\n") }
            field.constraints.maxLength?.let { code.append("    [MaxLength($it)]\n") }
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