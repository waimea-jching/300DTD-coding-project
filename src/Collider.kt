//=============================================================================================


import java.awt.Dimension
import java.awt.Rectangle
import kotlin.math.abs


//=============================================================================================


class Collider(private var bounds : Rectangle, private val gameDisplay: Display){

    private val displayBoundary : Dimension = gameDisplay.displayBoundary
    private var collisionBodys = mutableListOf<Rectangle>()

    companion object {
        val globalColliders = mutableListOf<Collider>()
    }

    init {
        globalColliders.add(this)
    }

    fun updateCollider(newBounds : Rectangle) {
        bounds = newBounds

    }

    fun isColliding() : Boolean{
        var isColliding = false

        val padding = 10
        val sensitivePadding = 13
        val paddedBounds = Rectangle(bounds.x - padding, bounds.y - padding, bounds.width + sensitivePadding, bounds.height + sensitivePadding)

        for (collider in globalColliders){
            if (collider != this){
                if (paddedBounds.intersects(collider.bounds)) {
                    isColliding = true
                    if (!collisionBodys.contains(collider.bounds)) collisionBodys.add(collider.bounds)
                    break
                }
                else {
                    if (collisionBodys.contains(collider.bounds)) collisionBodys.remove(collider.bounds)
                }
            }
        }

        //Checking if object is touching or beyond display boundaries
        if (bounds.x <= 0) isColliding = true
        if (bounds.y <= 0) isColliding = true
        if ((bounds.x + bounds.width) >= displayBoundary.width)  isColliding = true
        if ((bounds.y + bounds.height) >= displayBoundary.height)  isColliding = true

        return isColliding
    }

    fun getCollisionDirection() : Dimension{
        var collisionDirection = Dimension(0, 0)

        if (collisionBodys.isNotEmpty()){
            for (collision in collisionBodys){
                val padding = 14

                //up
                var shiftedBounds = Rectangle(bounds.x, bounds.y - padding, bounds.width, bounds.height)
                if (shiftedBounds.intersects(collision.bounds)){
                    collisionDirection = Dimension(collisionDirection.width, -1)
                }
                //down
                shiftedBounds = Rectangle(bounds.x, bounds.y + padding, bounds.width, bounds.height)
                if (shiftedBounds.intersects(collision.bounds)){
                    collisionDirection = Dimension(collisionDirection.width, 1)
                }
                //left
                shiftedBounds = Rectangle(bounds.x - padding, bounds.y, bounds.width, bounds.height)
                if (shiftedBounds.intersects(collision.bounds)){
                    collisionDirection = Dimension(-1,collisionDirection.height)
                }
                //right
                shiftedBounds = Rectangle(bounds.x + padding, bounds.y, bounds.width, bounds.height)
                if (shiftedBounds.intersects(collision.bounds)){
                    collisionDirection = Dimension(1,collisionDirection.height)
                }
            }
        }
        
        //collision direction against display boundaries
        if (bounds.x <= 0) collisionDirection = Dimension(-1,collisionDirection.height)
        if (bounds.y <= 0) collisionDirection = Dimension(collisionDirection.width, -1)
        if ((bounds.x + bounds.width) >= displayBoundary.width) collisionDirection = Dimension(1,collisionDirection.height)
        if ((bounds.y + bounds.height) >= displayBoundary.height) collisionDirection = Dimension(collisionDirection.width, 1)

        return collisionDirection
    }
}