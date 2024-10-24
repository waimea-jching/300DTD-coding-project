//=============================================================================================


import java.awt.Dimension
import java.awt.Rectangle


//=============================================================================================


class Collider(private var colliderBound : Rectangle, private val gameDisplay: Display){
    //initializing
    private val displaySize : Dimension = gameDisplay.contentPane.preferredSize

    companion object {
        val globalColliders = mutableListOf<Collider>()
    }

    init {
        globalColliders.add(this)
    }

    fun updateCollider(newBounds : Rectangle) {
        colliderBound = newBounds
    }

    fun isColliding() : Boolean{
        //return colliderBound.contains(colliderBound)

        //Checking if parent is touching or
        // beyond boundaries
        if (colliderBound.x <= 0) return true
        if (colliderBound.y <= 0) return true
        if ((colliderBound.x + colliderBound.width) >= displaySize.width)  return true
        if ((colliderBound.y + colliderBound.height) >= displaySize.height)  return true

        return false
    }

    fun getCollisionDirection() : Dimension{
        var collisionDirection = Dimension(0, 0)

        if (colliderBound.x <= 0) collisionDirection = Dimension(-1,collisionDirection.height)
        if (colliderBound.y <= 0) collisionDirection = Dimension(collisionDirection.width, -1)
        if ((colliderBound.x + colliderBound.width) >= displaySize.width) collisionDirection = Dimension(1,collisionDirection.height)
        if ((colliderBound.y + colliderBound.height) >= displaySize.height) collisionDirection = Dimension(collisionDirection.width, 1)

        return collisionDirection
    }
}