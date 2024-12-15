package UI.Main.Panels

import Common.CodeGenerators.CSharp.CodeGeneratorCSharp
import Common.CodeGenerators.CodeGenerator
import Common.CodeGenerators.Typescript.CodeGeneratorTypeScript

object CodeGeneratorFactory {
    fun getGenerators(): Map<String, CodeGenerator> = mapOf(
        "C#" to CodeGeneratorCSharp(),
        "TypeScript" to CodeGeneratorTypeScript(),
        "Kotlin" to Common.CodeGenerators.CodeGeneratorKotlin(),
        "Python" to Common.CodeGenerators.CodeGeneratorPython(),
        "Java" to Common.CodeGenerators.CodeGeneratorJava(),
        "MySQL" to Common.CodeGenerators.CodeGeneratorMySQL(),
        "Go" to Common.CodeGenerators.CodeGeneratorGo(),
        "C++" to Common.CodeGenerators.CodeGeneratorCpp()
    )
}
