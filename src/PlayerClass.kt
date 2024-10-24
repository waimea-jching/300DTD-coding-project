//=============================================================================================


import java.awt.Dimension
import java.awt.KeyEventDispatcher
import java.awt.Rectangle
import java.awt.event.KeyEvent
import javax.swing.JLabel
import kotlin.math.*


//=============================================================================================


class Player(private val gameDisplay: Display): JLabel(), KeyEventDispatcher {
    //components
    private val playerCollider : Collider
    private val playerAnimator : Animator

    //movement
    private var verticalInput : Int = 0
    private var horizontalInput : Int = 0
    private val moveSpeed : Int = 3

    //animation
    private lateinit var idleAnimation : Animation

    //physics
    private var isColliding : Boolean = false

    init {
        //set starting position and size of player here
        bounds = Rectangle(380,280,80,80)

        //setup components & animations
        playerCollider = Collider(bounds, gameDisplay)
        playerAnimator = Animator()
        setUpAnimations()
    }

    private fun setUpAnimations() {
        val paths = mutableListOf<String>()

        paths.addAll(listOf("src/images/frame_0_delay-0.05s.png", "src/images/frame_1_delay-0.05s.png", "src/images/frame_2_delay-0.05s.png",
                            "src/images/frame_3_delay-0.05s.png", "src/images/frame_4_delay-0.05s.png", "src/images/frame_5_delay-0.05s.png",
                            "src/images/frame_6_delay-0.05s.png", "src/images/frame_7_delay-0.05s.png"))
        idleAnimation = Animation(paths, 15, bounds)

        playerAnimator.setAnimation(idleAnimation)
        playerAnimator.playAnimation()
    }

    fun animatePlayer() {
        icon = playerAnimator.getCurrentFrame()
    }

    fun movePlayer() {
        val newPosition = bounds
        val calculatedMoveSpeed : Int

        //check if player is moving diagonally and normalise
        // the speed based on the input vector
        if (horizontalInput != 0 && verticalInput != 0) {
            val inputVector = sqrt((horizontalInput.toFloat() * horizontalInput.toFloat()) + (verticalInput.toFloat() * verticalInput.toFloat()))
            calculatedMoveSpeed = (moveSpeed.toFloat() / inputVector).toInt()
        }
        else calculatedMoveSpeed = moveSpeed

        if (!isColliding) {
            newPosition.x += (horizontalInput * calculatedMoveSpeed)
            newPosition.y += (verticalInput * calculatedMoveSpeed)
            bounds = newPosition
        }
        else {
            //if colliding we stop the player from
            // moving in the direction of the collision
            val collisionDirection : Dimension = playerCollider.getCollisionDirection()
            if (collisionDirection.width != horizontalInput) newPosition.x += (horizontalInput * moveSpeed)
            if (collisionDirection.height != verticalInput) newPosition.y += (verticalInput * moveSpeed)
            bounds = newPosition
        }

        //update player collider & add new positioned
        // player to game display
        playerCollider.updateCollider(newPosition)
    }

    fun playerCollisionCheck(){
        isColliding = playerCollider.isColliding()
    }

    override fun dispatchKeyEvent(e: KeyEvent?): Boolean {
        // Was it a key press event
        if(e?.id == KeyEvent.KEY_PRESSED) {
            when (e.keyCode) {
                KeyEvent.VK_W -> verticalInput = -1
                KeyEvent.VK_A -> horizontalInput = -1
                KeyEvent.VK_S -> verticalInput = 1
                KeyEvent.VK_D -> horizontalInput = 1
            }
        }
        //was it a release event
        if(e?.id == KeyEvent.KEY_RELEASED) {
            when (e.keyCode) {
                //checking if the player is moving before
                // culling input on key release
                KeyEvent.VK_W -> if (verticalInput != 1)verticalInput = 0
                KeyEvent.VK_A -> if (horizontalInput != 1) horizontalInput = 0
                KeyEvent.VK_S -> if (verticalInput != -1)verticalInput = 0
                KeyEvent.VK_D -> if (horizontalInput != -1) horizontalInput = 0
            }
        }
        return false
    }
}
