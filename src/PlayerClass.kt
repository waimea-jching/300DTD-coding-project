//=============================================================================================


import java.awt.Dimension
import java.awt.KeyEventDispatcher
import java.awt.Rectangle
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent
import javax.swing.JLabel
import javax.swing.Timer
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

    //Attacking
    private var hitBox : Rectangle = Rectangle(0, 0, 0, 0)
    private val hitArea = 40
    private val damage : Int = 25

    //Health
    var health : Int = 100
    var isDead = false

    //animation
    private lateinit var idleAnimation : Animation
    private lateinit var runAnimation : Animation
    private lateinit var idleLAnimation : Animation
    private lateinit var runLAnimation : Animation
    private var isLeft : Boolean = false

    //physics
    private var isColliding : Boolean = false

    init {
        //set starting position and size of player here
        bounds = Rectangle(380,280,54,66)
        hitBox = Rectangle(bounds.x - hitArea, bounds.y - hitArea, bounds.width + (hitArea * 2), bounds.height + (hitArea * 2))


        //setup components & animations
        playerCollider = Collider(this, null, bounds, gameDisplay)
        playerAnimator = Animator()
        setUpAnimations()
    }

    private fun setUpAnimations() {
        val paths = mutableListOf<String>()

        paths.addAll(listOf("src/Animations/Player_Idle/frame_0.png", "src/Animations/Player_Idle/frame_1.png",
                            "src/Animations/Player_Idle/frame_2.png", "src/Animations/Player_Idle/frame_3.png",
                            "src/Animations/Player_Idle/frame_4.png", "src/Animations/Player_Idle/frame_5.png",
                            "src/Animations/Player_Idle/frame_6.png", "src/Animations/Player_Idle/frame_7.png",))
        idleAnimation = Animation(paths, 5, bounds)

        paths.clear()
        paths.addAll(listOf("src/Animations/Player_Idle_Left/frame_0.png", "src/Animations/Player_Idle_Left/frame_1.png",
                            "src/Animations/Player_Idle_Left/frame_2.png", "src/Animations/Player_Idle_Left/frame_3.png",
                            "src/Animations/Player_Idle_Left/frame_4.png", "src/Animations/Player_Idle_Left/frame_5.png",
                            "src/Animations/Player_Idle_Left/frame_6.png", "src/Animations/Player_Idle_Left/frame_7.png",))
        idleLAnimation = Animation(paths, 5, bounds)

        paths.clear()
        paths.addAll(listOf("src/Animations/Player_Run/frame_0.png", "src/Animations/Player_Run/frame_1.png",
                            "src/Animations/Player_Run/frame_2.png", "src/Animations/Player_Run/frame_3.png",
                            "src/Animations/Player_Run/frame_4.png", "src/Animations/Player_Run/frame_5.png",
                            "src/Animations/Player_Run/frame_6.png", "src/Animations/Player_Run/frame_7.png",))
        runAnimation = Animation(paths, 5, bounds)

        paths.clear()
        paths.addAll(listOf("src/Animations/Player_Run_Left/frame_0.png", "src/Animations/Player_Run_Left/frame_1.png",
                            "src/Animations/Player_Run_Left/frame_2.png", "src/Animations/Player_Run_Left/frame_3.png",
                            "src/Animations/Player_Run_Left/frame_4.png", "src/Animations/Player_Run_Left/frame_5.png",
                            "src/Animations/Player_Run_Left/frame_6.png", "src/Animations/Player_Run_Left/frame_7.png",))
        runLAnimation = Animation(paths, 5, bounds)

        playerAnimator.setAnimation(idleAnimation)
        playerAnimator.loop = true
        playerAnimator.playAnimation()
    }

    fun animatePlayer() {
        if (verticalInput != 0 || horizontalInput != 0){
            if (horizontalInput == 1){
                if (playerAnimator.getCurrentAnimation() != runAnimation) {
                    isLeft = false
                    playerAnimator.setAnimation(runAnimation)
                    playerAnimator.playAnimation()
                }
            }
            else if (horizontalInput == -1){
                if (playerAnimator.getCurrentAnimation() != runLAnimation) {
                    isLeft = false
                    playerAnimator.setAnimation(runLAnimation)
                    playerAnimator.playAnimation()
                }
            }
            else {
                if (playerAnimator.getCurrentAnimation() == idleLAnimation || playerAnimator.getCurrentAnimation() == runLAnimation) {
                    if (playerAnimator.getCurrentAnimation() != runLAnimation) {
                        isLeft = true
                        playerAnimator.setAnimation(runLAnimation)
                        playerAnimator.playAnimation()
                    }
                }
                else {
                    if (playerAnimator.getCurrentAnimation() != runAnimation) {
                        isLeft = false
                        playerAnimator.setAnimation(runAnimation)
                        playerAnimator.playAnimation()
                    }
                }
            }
        }
        else {
            if (playerAnimator.getCurrentAnimation() == runAnimation){
                if (playerAnimator.getCurrentAnimation() != idleAnimation) {
                    isLeft = false
                    playerAnimator.setAnimation(idleAnimation)
                    playerAnimator.playAnimation()
                }
            }
            else if (playerAnimator.getCurrentAnimation() == runLAnimation){
                if (playerAnimator.getCurrentAnimation() != idleLAnimation) {
                    isLeft = true
                    playerAnimator.setAnimation(idleLAnimation)
                    playerAnimator.playAnimation()
                }
            }
        }

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
        hitBox = Rectangle(bounds.x - hitArea, bounds.y - hitArea, bounds.width + (hitArea * 2), bounds.height + (hitArea * 2))
    }

    fun attack(){
        val hits = playerCollider.getCustomCollision(hitBox)
        if (hits.isNotEmpty()) {
            for (hit in hits) {
                if (hit.classEnemy != null){
                    hit.classEnemy?.hurt(damage)
                }
            }
        }
    }

    fun hurt(damage : Int){
        health -= damage
        if (health <= 0){
            health = 0
            die()
        }
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
                KeyEvent.VK_SHIFT -> attack()
            }
        }
        return false
    }

    private fun die(){
        isDead = true
        isVisible = false
    }
}
