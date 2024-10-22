//=============================================================================================


import java.awt.Dimension
import java.awt.Rectangle
import java.util.Vector
import kotlin.math.atan
import kotlin.math.tan


//=============================================================================================


class Vector2 (var x : Double, var y : Double) {
    fun normalize(){
        val vectorDirection : Double = atan((x/y))
        val newX : Double = x / vectorDirection
    }

    fun toDimension() : Dimension {
        return Dimension(x.toInt(), y.toInt())
    }

    fun toBounds(width : Int, height : Int) : Rectangle {
        return Rectangle(x.toInt(),y.toInt(),width,height)
    }
}
