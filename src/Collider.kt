//=============================================================================================


import java.awt.Dimension
import java.awt.Rectangle


//=============================================================================================


class Collider(private var bounds : Rectangle, private val gameDisplay: Display){

    private val displayBoundary : Dimension = gameDisplay.displayBoundary
    private lateinit var collisionReport : Collision

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

        for (collider in globalColliders){
            if (collider != this){
                isColliding = bounds.intersects(collider.bounds)
                if (isColliding) {
                    collisionReport = Collision(this, collider)
                    break
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

        if (collisionReport != null){
            
        }

        //collision direction against display boundaries
        if (bounds.x <= 0) collisionDirection = Dimension(-1,collisionDirection.height)
        if (bounds.y <= 0) collisionDirection = Dimension(collisionDirection.width, -1)
        if ((bounds.x + bounds.width) >= displayBoundary.width) collisionDirection = Dimension(1,collisionDirection.height)
        if ((bounds.y + bounds.height) >= displayBoundary.height) collisionDirection = Dimension(collisionDirection.width, 1)

        return collisionDirection
    }
}