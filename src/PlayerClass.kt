//=============================================================================================


import java.awt.Dimension
import java.awt.KeyEventDispatcher
import java.awt.Rectangle
import java.awt.event.KeyEvent
import javax.swing.JLabel


//=============================================================================================


class Player(private val gameDisplay: Display): JLabel(), KeyEventDispatcher {
    private var verticalInput : Int = 0
    private var horizontalInput : Int = 0

    private val moveSpeed : Int = 10

    private val playerCollider : Collider
    private val playerAnimator : Animator
    private lateinit var idleAnimation : Animation
    private lateinit var testAnimation : Animation

    private var isColliding : Boolean = false

    init {
        bounds = Rectangle(380,280,40,40)

        playerCollider = Collider(bounds, gameDisplay)
        playerAnimator = Animator()
        setUpAnimations()


        gameDisplay.add(this)
    }

    private fun setUpAnimations() {
        val paths = mutableListOf<String>()

        paths.addAll(listOf("src/images/chracter.png", "src/images/chractercopy.png"))
        idleAnimation = Animation(paths, bounds)

        paths.clear()
        paths.addAll(listOf("src/images/test.png", "src/images/test2.jpg", "src/images/test3.jpg"))
        testAnimation = Animation(paths, bounds)

        playerAnimator.setAnimation(idleAnimation)
        playerAnimator.setAnimationSpeed(2)
        playerAnimator.playAnimation()
    }

    fun animatePlayer() {
        icon = playerAnimator.getCurrentAnimationFrame()
    }

    fun movePlayer() {
        val currentPosition = bounds
        val newPosition = currentPosition

        if (!isColliding) {
            newPosition.x += (horizontalInput * moveSpeed)
            newPosition.y += (verticalInput * moveSpeed)
            bounds = newPosition
        }
        else {
            val collisionDirection : Dimension = playerCollider.getCollisionDirection()
            if (collisionDirection.width != horizontalInput) newPosition.x += ((horizontalInput * moveSpeed))
            if (collisionDirection.height != verticalInput) newPosition.y += ((verticalInput * moveSpeed))
            bounds = newPosition
        }

        playerCollider.updateCollider(newPosition)
        gameDisplay.add(this)
    }

    fun playerCollisionCheck(){
        isColliding = playerCollider.isColliding()
    }

    override fun dispatchKeyEvent(e: KeyEvent?): Boolean {
        // Was it a key press event
        if(e?.id == KeyEvent.KEY_PRESSED) {
            // Take action
            when (e.keyCode) {
                KeyEvent.VK_W -> verticalInput = -1
                KeyEvent.VK_A -> horizontalInput = -1
                KeyEvent.VK_S -> verticalInput = 1
                KeyEvent.VK_D -> horizontalInput = 1
                KeyEvent.VK_V -> playerAnimator.setAnimation(testAnimation)
            }
        }
        //was it a release event
        if(e?.id == KeyEvent.KEY_RELEASED) {
            // Take action
            when (e.keyCode) {
                KeyEvent.VK_W -> if (verticalInput != 1)verticalInput = 0
                KeyEvent.VK_A -> if (horizontalInput != 1) horizontalInput = 0
                KeyEvent.VK_S -> if (verticalInput != -1)verticalInput = 0
                KeyEvent.VK_D -> if (horizontalInput != -1) horizontalInput = 0
            }
        }
        // Allow the event to be redispatched
        return false
    }
}
