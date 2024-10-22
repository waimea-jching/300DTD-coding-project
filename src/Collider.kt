//=============================================================================================


import java.awt.Dimension
import java.awt.Rectangle


//=============================================================================================


class Collider(private val initialBounds : Rectangle, gameDisplay: Display){
    private var colliderBound : Rectangle = initialBounds
    private val displaySize : Dimension = gameDisplay.contentPane.preferredSize

    fun updateCollider(newBounds : Rectangle) {
        colliderBound = newBounds
    }

    fun isColliding() : Boolean{
        //return colliderBound.contains(colliderBound)

        if (colliderBound.x <= 0) return true
        if (colliderBound.y <= 0) return true
        if ((colliderBound.x + colliderBound.width) >= displaySize.width)  return true
        else return false
    }

    fun getCollisionDirection() : Dimension{
        var collisionDirection = Dimension(0, 0)

        if (colliderBound.x == 0) collisionDirection = Dimension(-1,collisionDirection.height)
        if (colliderBound.y == 0) collisionDirection = Dimension(collisionDirection.width, -1)
        if ((colliderBound.x + colliderBound.width) == displaySize.width) collisionDirection = Dimension(1,collisionDirection.height)
        if ((colliderBound.y + colliderBound.height) == displaySize.height) collisionDirection = Dimension(collisionDirection.width, 1)

        return collisionDirection
    }
}