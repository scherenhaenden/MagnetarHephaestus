package Common.CodeGenerators.Typescript

import BusinessLogic.Models.Field

class FieldCodeGeneratorTypeScript {
    fun generateFieldCode(
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
}