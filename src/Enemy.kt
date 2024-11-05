import java.awt.Dimension
import java.awt.Rectangle
import javax.swing.JLabel
import kotlin.math.sqrt
import kotlin.random.Random

class Enemy(private val gameDisplay: Display, private val player : Player) : JLabel() {
    private val enemyAnimator : Animator
    private val enemyCollider : Collider

    private val enemySpawnPadding : Int = 27

    private lateinit var idleAnimation : Animation
    private var isLeft : Boolean = false

    private val aggressionDistance : Int = 120
    private val aggressionPadding : Int = 3
    private var isNearPlayer : Boolean = false
    private val moveSpeed : Int = 2
    private var isColliding : Boolean = false

    init {
        val spawnArea = Rectangle(enemySpawnPadding, enemySpawnPadding, gameDisplay.displayBoundary.width - (enemySpawnPadding * 2), gameDisplay.displayBoundary.height - (enemySpawnPadding * 2))
        val xCoordinate = Random.nextInt(spawnArea.x ,spawnArea.width)
        val yCoordinate = Random.nextInt(spawnArea.y ,spawnArea.height)

        bounds = Rectangle (xCoordinate, yCoordinate, 72, 70)

        enemyCollider = Collider(bounds, gameDisplay)
        enemyAnimator = Animator()

        setupAnimations()
    }

    fun checkForPlayer() {
        val aggressionBounds = Rectangle (bounds.x - aggressionDistance, bounds.y - aggressionDistance, bounds.x + bounds.width + aggressionDistance, bounds.y + bounds.height + aggressionDistance)

        isNearPlayer = aggressionBounds.intersects(player.bounds)
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

        enemyAnimator.setAnimation(idleAnimation)
        enemyAnimator.loop = true
        enemyAnimator.playAnimation()
    }

    fun animateEnemy() {
        icon = enemyAnimator.getCurrentFrame()
    }
}