package Common.CodeGenerators.Typescript

import BusinessLogic.Models.Model

class MapperCodeGeneratorTypeScript {
    fun generateMapperCode(
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