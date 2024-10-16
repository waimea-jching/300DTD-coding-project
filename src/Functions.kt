import java.awt.Dimension
import java.awt.Rectangle

class Vector2 (var x : Int, var y : Int) {
    fun normalize() {

    }

    fun toDimension() : Dimension {
        return Dimension(0, 0)
    }

    fun toBounds() : Rectangle {
        return Rectangle(0,0,0,0)
    }

}
