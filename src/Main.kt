//=============================================================================================


import com.formdev.flatlaf.FlatDarkLaf
import com.formdev.flatlaf.FlatLaf
import java.awt.*
import javax.swing.ImageIcon
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.SwingConstants
import kotlin.math.abs


//=============================================================================================


/**
 * ------------------------------------------------------------------------
 * Dungeon Knight
 * Level 3 programming project
 *
 * by Josiah Ching
 *
 * BRIEF PROJECT DESCRIPTION HERE
 * BRIEF PROJECT DESCRIPTION HERE
 * BRIEF PROJECT DESCRIPTION HERE
 * ------------------------------------------------------------------------
 */

//Displays
lateinit var gameDisplay : Display
lateinit var background : JLabel

lateinit var testObject: JButton
lateinit var testObject2: JButton

//Player Instance
lateinit var player : Player

fun main(){
    awake()
}

fun awake(){
    //Flat, Dark look
    FlatDarkLaf.setup()

    //Load background Icon
    var backgroundImage = ImageIcon("src/images/gridBackground.png").image
    backgroundImage = backgroundImage.getScaledInstance(800, 600, Image.SCALE_SMOOTH)
    val backgroundIcon = ImageIcon(backgroundImage)

    //Create displays
    gameDisplay = Display("Dungeon Knight", Dimension(800, 600), Dimension(800,600))

    //Create Background
    background = JLabel()
    background.bounds = Rectangle(0, 0, 800, 600)
    background.icon = backgroundIcon
    gameDisplay.add(background)

    //Collision Testing
    testObject = TestObject(gameDisplay, Dimension(40, 40))
    background.add(testObject)

    testObject2 = TestObject(gameDisplay, Dimension(600, 450))
    background.add(testObject2)

    //Instantiate Player & Allow to Read Input
    player = Player(gameDisplay)
    background.add(player)
    KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(player)

    //Start Timers
    setupTimers()
}

fun update(){
    player.playerCollisionCheck()
    player.animatePlayer()
    player.movePlayer()
}

class TestObject(private val gameDisplay : Display, private val spawn : Dimension): JButton("+") {
    private val objectCollider : Collider

    val flatLafFont = FlatLaf.getPreferredFontFamily()
    val bigFont = Font(flatLafFont, Font.PLAIN, 40)

    init {
        bounds = Rectangle(spawn.width, spawn.height, 80, 80)
        font = bigFont

        objectCollider = Collider(bounds, gameDisplay)
    }
}





