//=============================================================================================


import com.formdev.flatlaf.FlatLaf
import java.awt.Dimension
import java.awt.Font
import java.awt.KeyEventDispatcher
import java.awt.Rectangle
import java.awt.event.KeyEvent
import javax.swing.JLabel
import javax.swing.SwingConstants


//=============================================================================================


class Player(val gameDisplay: Display): JLabel("*", SwingConstants.CENTER), KeyEventDispatcher {
    private val flatLafFont = FlatLaf.getPreferredFontFamily()
    private val bigFont = Font(flatLafFont, Font.PLAIN, 40)

    private var verticalInput : Int = 0
    private var horizontalInput : Int = 0

    private val moveSpeed : Int = 10

    private lateinit var playerCollider : Collider

    private var isColliding : Boolean = false

    init {
        font = bigFont
        bounds = Rectangle(380,280,40,40)
        playerCollider = Collider(bounds, gameDisplay)
        gameDisplay.add(this)
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
            }
        }
        //was it a release event
        if(e?.id == KeyEvent.KEY_RELEASED) {
            // Take action
            when (e.keyCode) {
                KeyEvent.VK_W -> verticalInput = 0
                KeyEvent.VK_A -> horizontalInput = 0
                KeyEvent.VK_S -> verticalInput = -0
                KeyEvent.VK_D -> horizontalInput = 0
            }
        }
        // Allow the event to be redispatched
        return false
    }
}
