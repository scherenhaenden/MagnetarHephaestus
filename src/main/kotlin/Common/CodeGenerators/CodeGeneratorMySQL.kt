package Common.CodeGenerators

import BusinessLogic.Models.APIModeler
import BusinessLogic.Models.Constraints
import BusinessLogic.Models.Field
import BusinessLogic.Models.Model

class CodeGeneratorMySQL : CodeGenerator {
    override fun generateCode(
        apiModeler: APIModeler,
        includeComments: Boolean,
        includeDecorators: Boolean,
        layerModels: Boolean
    ): String {
        val code = StringBuilder()
        code.append("-- Generated MySQL code for ${apiModeler.modelName}\n\n")

        apiModeler.models.forEach { model ->
            if (layerModels) {
                // Generate tables for each layer
                apiModeler.modelDetails.layer.forEach { layer ->
                    code.append(generateTableCode(model, includeComments, layer))
                }
            } else {
                // Generate a single table (existing behavior)
                code.append(generateTableCode(model, includeComments))
            }
        }
        return code.toString()
    }

    private fun generateTableCode(model: Model, includeComments: Boolean, layer: String? = null): String {
        val code = StringBuilder()
        if (includeComments) {
            code.append("-- Table: ${model.name}\n")
            code.append("-- ${model.description}\n")
        }

        // Generate table name with layer (if provided)
        val tableName = if (layer != null) {
            "${model.name}_$layer" // Example: dog_business_logic
        } else {
            model.name
        }
        code.append("CREATE TABLE $tableName (\n")

        model.fields.forEachIndexed { index, field ->
            code.append(generateFieldCode(field, includeComments))
            if (index < model.fields.size - 1) {
                code.append(",\n")
            }
        }
        code.append("\n);\n\n")
        return code.toString()
    }

    private fun generateFieldCode(field: Field, includeComments: Boolean): String {
        val code = StringBuilder()
        if (includeComments && field.description.isNotEmpty()) {
            code.append("    -- ${field.description}\n")
        }
        val mysqlType = getMySQLType(field.dataType, field.constraints)
        code.append("    ${field.name} $mysqlType")
        if (field.constraints.required) {
            code.append(" NOT NULL")
        }
        if (field.constraints.enum != null) {
            code.append(" CHECK (${field.name} IN (${field.constraints.enum.joinToString(", ") { "'${it}'" }}))")
        }
        if (field.name == "id") {
            code.append(" PRIMARY KEY") // Assume 'id' is the primary key
        }
        return code.toString()
    }

    private fun getMySQLType(dataType: String, constraints: Constraints?): String {
        return when (dataType.lowercase()) {
            "string" -> "VARCHAR(${constraints?.maxLength ?: 255})"
            "int" -> "INT"
            "boolean" -> "BOOLEAN"
            "float" -> "FLOAT"
            "double" -> "DOUBLE"
            "uuid" -> "CHAR(36)"
            else -> "TEXT"
        }
    }
}