import java.awt.Dimension
import java.awt.Rectangle
import java.awt.event.ActionListener
import javax.swing.JLabel
import kotlin.math.sqrt
import kotlin.random.Random
import javax.swing.Timer

class Enemy(private val gameDisplay: Display, private val player : Player) : JLabel() {
    //components
    val enemyAnimator : Animator
    val enemyCollider : Collider

    private val enemySpawnPadding : Int = 27

    //Attacking
    private var hitBox : Rectangle = Rectangle(0, 0, 0, 0)
    private val hitArea = 25
    private val damage : Int = 5
    private val attackListener : ActionListener = ActionListener {attack()}
    val attackTimer : Timer = Timer(900, attackListener)

    //Health
    var health : Int = 100
    var isDead = false

    //Animations
    private lateinit var idleAnimation : Animation
    private lateinit var idleLAnimation : Animation
    private lateinit var runAnimation : Animation
    private lateinit var runLAnimation : Animation
    private var isLeft : Boolean = false

    //movement
    private val aggressionDistance : Int = 120
    private val aggressionPadding : Int = 3
    private var isNearPlayer : Boolean = false
    private var canAttack : Boolean = false
    private val moveSpeed : Int = 2
    private var isColliding : Boolean = false

    init {
        spawn()

        enemyCollider = Collider(null, this, bounds, gameDisplay)
        enemyAnimator = Animator()

        if (enemyCollider.isColliding()){
            spawn()
        }

        //create the attack area hit box
        hitBox = Rectangle(bounds.x - hitArea, bounds.y - hitArea, bounds.width + (hitArea * 2), bounds.height + (hitArea * 2))

        if (Random.nextInt(0, 1) == 1) isLeft = true
        else isLeft = false

        setupAnimations()

        gameDisplay.background.add(this)
    }

    fun checkForPlayer() {
        //create check areas
        val aggressionBounds = Rectangle (bounds.x - aggressionDistance, bounds.y - aggressionDistance,  bounds.width + (aggressionDistance * 2), bounds.height + (aggressionDistance * 2))
        hitBox = Rectangle(bounds.x - hitArea, bounds.y - hitArea, bounds.width + (hitArea * 2), bounds.height + (hitArea * 2))

        //check if they intersect with player
        isNearPlayer = aggressionBounds.intersects(player.bounds)

        canAttack = hitBox.intersects(player.bounds)
        if (canAttack) attackTimer.start()
        else attackTimer.stop()
    }

    fun moveEnemy() {
        if (isNearPlayer) {
            val newPosition = bounds

            val moveDirection = getDirectionToPlayer()
            var calculatedMoveSpeed : Int = moveSpeed

            if (moveDirection.width != 0 && moveDirection.height != 0) {
                val moveVector = sqrt((moveDirection.width.toFloat() * moveDirection.width.toFloat()) + (moveDirection.height.toFloat() * moveDirection.height.toFloat()))
                calculatedMoveSpeed = (moveSpeed.toFloat() / moveVector).toInt()
            }

            if (!isColliding) {
                newPosition.x += moveDirection.width * calculatedMoveSpeed
                newPosition.y += moveDirection.height * calculatedMoveSpeed

                bounds = newPosition
                enemyCollider.updateCollider(newPosition)
            }
            else {
                val collisionDirection : Dimension = enemyCollider.getCollisionDirection()
                if (collisionDirection.width != moveDirection.width) newPosition.x += (moveDirection.width * moveSpeed)
                if (collisionDirection.height != moveDirection.height) newPosition.y += (moveDirection.height * moveSpeed)

                bounds = newPosition
            }

            enemyCollider.updateCollider(newPosition)
        }
    }

    fun attack(){
        player.hurt(damage)
    }

    fun hurt(damage : Int){
        health -= damage
        if (health <= 0 && !isDead) {
            die()
        }
    }

    fun enemyCollisionCheck() {
        isColliding = enemyCollider.isColliding()
    }

    private fun getDirectionToPlayer() : Dimension {
        var direction = Dimension(0, 0)

        if (player.bounds.x >= bounds.x) direction = Dimension(1, direction.height)
        if (player.bounds.x <= bounds.x) direction = Dimension(-1, direction.height)
        if (player.bounds.y <= bounds.y) direction = Dimension(direction.width, -1)
        if (player.bounds.y >= bounds.y) direction = Dimension(direction.width, 1)

        if (bounds.x >= player.bounds.x - aggressionPadding && bounds.x <= player.bounds.x + aggressionPadding) direction = Dimension (0, direction.height)
        if (bounds.y >= player.bounds.y - aggressionPadding && bounds.y <= player.bounds.y + aggressionPadding) direction = Dimension (direction.width, 0)

        return direction
    }

    private fun setupAnimations() {
        val paths = mutableListOf<String>()

        paths.addAll(listOf("src/Animations/Enemy_Idle/frame_0.png", "src/Animations/Enemy_Idle/frame_1.png",
            "src/Animations/Enemy_Idle/frame_2.png", "src/Animations/Enemy_Idle/frame_3.png",
            "src/Animations/Enemy_Idle/frame_4.png", "src/Animations/Enemy_Idle/frame_5.png",
            "src/Animations/Enemy_Idle/frame_6.png", "src/Animations/Enemy_Idle/frame_7.png",))
        idleAnimation = Animation(paths, 5, bounds)

        paths.clear()
        paths.addAll(listOf("src/Animations/Enemy_Idle_Left/frame_0.png", "src/Animations/Enemy_Idle_Left/frame_1.png",
            "src/Animations/Enemy_Idle_Left/frame_2.png", "src/Animations/Enemy_Idle_Left/frame_3.png",
            "src/Animations/Enemy_Idle_Left/frame_4.png", "src/Animations/Enemy_Idle_Left/frame_5.png",
            "src/Animations/Enemy_Idle_Left/frame_6.png", "src/Animations/Enemy_Idle_Left/frame_7.png",))
        idleLAnimation = Animation(paths, 5, bounds)

        paths.clear()
        paths.addAll(listOf("src/Animations/Enemy_Run/frame_0.png", "src/Animations/Enemy_Run/frame_1.png",
            "src/Animations/Enemy_Run/frame_2.png", "src/Animations/Enemy_Run/frame_3.png",
            "src/Animations/Enemy_Run/frame_4.png", "src/Animations/Enemy_Run/frame_5.png",
            "src/Animations/Enemy_Run/frame_6.png", "src/Animations/Enemy_Run/frame_7.png",))
        runAnimation = Animation(paths, 5, bounds)

        paths.clear()
        paths.addAll(listOf("src/Animations/Enemy_Run_Left/frame_0.png", "src/Animations/Enemy_Run_Left/frame_1.png",
            "src/Animations/Enemy_Run_Left/frame_2.png", "src/Animations/Enemy_Run_Left/frame_3.png",
            "src/Animations/Enemy_Run_Left/frame_4.png", "src/Animations/Enemy_Run_Left/frame_5.png",
            "src/Animations/Enemy_Run_Left/frame_6.png", "src/Animations/Enemy_Run_Left/frame_7.png",))
        runLAnimation = Animation(paths, 5, bounds)

        enemyAnimator.setAnimation(idleAnimation)
        enemyAnimator.loop = true
        enemyAnimator.playAnimation()
    }

    fun animateEnemy() {
        if (isNearPlayer){
            val moveDirection = getDirectionToPlayer()

            if (moveDirection.width > 0) isLeft = false
            else if (moveDirection.width < 0) isLeft = true

            if (!isColliding) {
                if (isLeft) {
                    if (enemyAnimator.getCurrentAnimation() != runLAnimation) {
                        enemyAnimator.setAnimation(runLAnimation)
                        enemyAnimator.playAnimation()
                    }
                }
                else {
                    if (enemyAnimator.getCurrentAnimation() != runAnimation) {
                        enemyAnimator.setAnimation(runAnimation)
                        enemyAnimator.playAnimation()
                    }
                }
            }
            else {
                val collisionDirection = enemyCollider.getCollisionDirection()

                if (collisionDirection.width != moveDirection.width || collisionDirection.height != moveDirection.height) {
                    if (isLeft) {
                        if (enemyAnimator.getCurrentAnimation() != runLAnimation) {
                            enemyAnimator.setAnimation(runLAnimation)
                            enemyAnimator.playAnimation()
                        }
                    }
                    else {
                        if (enemyAnimator.getCurrentAnimation() != runAnimation) {
                            enemyAnimator.setAnimation(runAnimation)
                            enemyAnimator.playAnimation()
                        }
                    }
                }
                else if (collisionDirection.width == moveDirection.width && collisionDirection.height == moveDirection.height) {
                    if (isLeft) {
                        if (enemyAnimator.getCurrentAnimation() != idleLAnimation) {
                            enemyAnimator.setAnimation(idleLAnimation)
                            enemyAnimator.playAnimation()
                        }
                    }
                    else {
                        if (enemyAnimator.getCurrentAnimation() != idleAnimation) {
                            enemyAnimator.setAnimation(idleAnimation)
                            enemyAnimator.playAnimation()
                        }
                    }
                }
            }
        }
        else {
            if (isLeft) {
                if (enemyAnimator.getCurrentAnimation() != idleLAnimation) {
                    enemyAnimator.setAnimation(idleLAnimation)
                    enemyAnimator.playAnimation()
                }
            }
            else {
                if (enemyAnimator.getCurrentAnimation() != idleAnimation) {
                    enemyAnimator.setAnimation(idleAnimation)
                    enemyAnimator.playAnimation()
                }
            }
        }

        icon = enemyAnimator.getCurrentFrame()
    }

    fun spawn() {
        val spawnArea = Rectangle(enemySpawnPadding, enemySpawnPadding, enemySpawnPadding + gameDisplay.displayBoundary.width - (enemySpawnPadding * 2), enemySpawnPadding + gameDisplay.displayBoundary.height - (enemySpawnPadding * 2))
        val xCoordinate = Random.nextInt(spawnArea.x ,spawnArea.width)
        val yCoordinate = Random.nextInt(spawnArea.y ,spawnArea.height)

        bounds = Rectangle (xCoordinate, yCoordinate, 58, 60)
    }

    fun die(){
        isDead = true
        attackTimer.stop()
        this.isVisible = false
        enemyCollider.destroyCollider()
        enemyAnimator.stopAnimation()
    }
}