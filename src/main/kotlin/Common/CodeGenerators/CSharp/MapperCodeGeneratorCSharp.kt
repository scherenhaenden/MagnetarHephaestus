package Common.CodeGenerators.CSharp

import BusinessLogic.Models.Model

class MapperCodeGeneratorCSharp {
    fun generateMapperCode(
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