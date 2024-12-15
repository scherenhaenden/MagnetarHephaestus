package Common.CodeGenerators.CSharp

import BusinessLogic.Models.APIModeler
import Common.CodeGenerators.CodeGenerator

class CodeGeneratorCSharp : CodeGenerator {
    override fun generateCode(
        apiModeler: APIModeler,
        includeComments: Boolean,
        includeDecorators: Boolean,
        layerModels: Boolean
    ): String {
        val code = StringBuilder()
        code.append("// Generated code for \${apiModeler.modelName}\n\n")

        val modelGenerator = ModelCodeGeneratorCSharp()
        val mapperGenerator = MapperCodeGeneratorCSharp()

        apiModeler.models.forEach { model ->
            if (layerModels) {
                // Generate models for each layer
                apiModeler.modelDetails.layer.forEach { layer ->
                    code.append(
                        modelGenerator.generateModelCode(model, includeComments, includeDecorators, layer)
                    )
                }

                // Generate mappers between layers (if there are 2 or more layers)
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
                // Generate a single model (existing behavior)
                code.append(
                    modelGenerator.generateModelCode(model, includeComments, includeDecorators)
                )
            }
        }
        return code.toString()
    }
}


