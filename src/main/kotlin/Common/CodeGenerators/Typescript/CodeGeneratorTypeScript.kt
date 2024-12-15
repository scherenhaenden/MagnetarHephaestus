package Common.CodeGenerators.Typescript

import BusinessLogic.Models.APIModeler
import BusinessLogic.Models.Model
import Common.CodeGenerators.CodeGenerator

class CodeGeneratorTypeScript : CodeGenerator {
    override fun generateCode(
        apiModeler: APIModeler,
        includeComments: Boolean,
        includeDecorators: Boolean,
        layerModels: Boolean
    ): String {
        val code = StringBuilder()
        code.append("// Generated code for ${apiModeler.modelName}\n\n")

        val modelGenerator = ModelCodeGeneratorTypeScript()
        val mapperGenerator = MapperCodeGeneratorTypeScript()

        apiModeler.models.forEach { model ->
            if (layerModels) {
                apiModeler.modelDetails.layer.forEach { layer ->
                    code.append(
                        modelGenerator.generateModelCode(model, includeComments, includeDecorators, layer)
                    )
                }

                if (apiModeler.modelDetails.layer.size >= 2) {
                    for (i in 0 until apiModeler.modelDetails.layer.size - 1) {
                        val sourceLayer = apiModeler.modelDetails.layer[i]
                        val targetLayer = apiModeler.modelDetails.layer[i + 1]
                        code.append(
                            mapperGenerator.generateMapperCode(model, sourceLayer, targetLayer, includeComments)
                        )
                    }
                }
            } else {
                code.append(
                    modelGenerator.generateModelCode(model, includeComments, includeDecorators)
                )
            }
        }

        return code.toString()
    }
}

class ModelCodeGeneratorTypeScript {
    fun generateModelCode(
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

        val className = if (layer != null) {
            "${model.name}${layer}Model"
        } else {
            model.name
        }
        code.append("export class $className {\n")

        val fieldGenerator = FieldCodeGeneratorTypeScript()
        model.fields.forEach { field ->
            code.append(fieldGenerator.generateFieldCode(field, includeComments, includeDecorators))
        }
        code.append("}\n\n")
        return code.toString()
    }
}

