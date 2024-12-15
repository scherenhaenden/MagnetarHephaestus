package Common.CodeGenerators.CSharp

import BusinessLogic.Models.Model

class ModelCodeGeneratorCSharp {
    fun generateModelCode(
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

        val className = if (layer != null) {
            "${model.name}${layer}Model"
        } else {
            model.name
        }
        code.append("public class $className {\n")

        val fieldGenerator = FieldCodeGeneratorCSharp()
        model.fields.forEach { field ->
            code.append(fieldGenerator.generateFieldCode(field, includeComments, includeDecorators))
        }
        code.append("}\n\n")
        return code.toString()
    }
}