package Common

import BusinessLogic.Models.APIModeler
/*import guru.nidi.graphviz.attribute.Label
import guru.nidi.graphviz.attribute.Shape
import guru.nidi.graphviz.engine.Format
import guru.nidi.graphviz.graph
import guru.nidi.graphviz.model.Factory.mutNode*/

/*fun generateGraphvizCode(apiModeler: APIModeler): String {
    val g = graph(apiModeler.modelName) {
        apiModeler.models.forEach { model ->
            val modelNode = mutNode(model.name).add(Label.html(model.description), Shape.BOX)
            model.properties.forEach { property ->
                val propertyLabel = buildString {
                    append("${property.name}: ${property.type}")
                    if (property.constraints.required) append(" (*)")
                }
                modelNode.addLink(mutNode(propertyLabel).add(Shape.ELLIPSE))
            }
        }
    }
    return g.render(Format.SVG).toString() // Or another format like PNG
}*/