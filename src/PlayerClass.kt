import com.formdev.flatlaf.FlatLaf
import jdk.incubator.vector.Vector
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.KeyEventDispatcher
import java.awt.Rectangle
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import javax.swing.JLabel
import javax.swing.SwingConstants

class Player(val currentDisplay: Display): JLabel("*", SwingConstants.CENTER), KeyEventDispatcher {
    val flatLafFont = FlatLaf.getPreferredFontFamily()
    val bigFont = Font(flatLafFont, Font.PLAIN, 40)

    var verticalInput : Int = 0
    var horizontalInput : Int = 0

    val moveSpeed : Int = 10

    lateinit var playerCollider : Collider

    private var isColliding : Boolean = false

    init {
        font = bigFont
        bounds = Rectangle(380,280,40,40)
        playerCollider = Collider(bounds, currentDisplay)
        currentDisplay.add(this)
    }

    fun movePlayer() {
        val currentPosition = bounds
        var newPosition = currentPosition

        if (!isColliding) {
            newPosition.x += ((horizontalInput * moveSpeed))
            newPosition.y += (verticalInput * moveSpeed)
            bounds = newPosition
        }
        else {
            val collisionDirection : Dimension = playerCollider.getCollisionDirection()
            if (collisionDirection.width != horizontalInput) newPosition.x += ((horizontalInput * moveSpeed))
            if (collisionDirection.height != verticalInput) newPosition.y += ((verticalInput * moveSpeed))
        }

        playerCollider.updateCollider(newPosition)
        currentDisplay.add(this)

        println(verticalInput)
        println(horizontalInput)
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
