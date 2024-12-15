package Common.CodeGenerators
import BusinessLogic.Models.APIModeler


interface CodeGenerator {
    fun generateCode(
        apiModeler: APIModeler,
        includeComments: Boolean = true,
        includeDecorators: Boolean = true,
        layerModels: Boolean = false // Parameter to enable/disable layer models
    ): String
}


