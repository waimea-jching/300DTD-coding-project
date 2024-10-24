//=============================================================================================


import com.formdev.flatlaf.FlatDarkLaf
import java.awt.*
import javax.swing.ImageIcon
import javax.swing.JLabel


//=============================================================================================


/**
 * ------------------------------------------------------------------------
 * PROJECT NAME HERE
 * Level 3 programming project
 *
 * by YOUR NAME HERE
 *
 * BRIEF PROJECT DESCRIPTION HERE
 * BRIEF PROJECT DESCRIPTION HERE
 * BRIEF PROJECT DESCRIPTION HERE
 * ------------------------------------------------------------------------
 */

//Displays
lateinit var gameDisplay : Display
lateinit var background : JLabel

//Player Instance
lateinit var player : Player

fun main(){
    Awake()
}

fun Awake(){
    //Flat, Dark look
    FlatDarkLaf.setup()

    //Load background Icon
    var backgroundImage = ImageIcon("src/images/gridBackground.png").image
    backgroundImage = backgroundImage.getScaledInstance(800, 600, Image.SCALE_SMOOTH)
    val backgroundIcon = ImageIcon(backgroundImage)

    //Create displays
    gameDisplay = Display("Dungeon Knight", Dimension(800, 600))

    //Create Background
    background = JLabel()
    background.bounds = Rectangle(0, 0, 800, 600)
    background.icon = backgroundIcon
    gameDisplay.add(background)

    //Instantiate Player & Allow to Read Input
    player = Player(gameDisplay)
    background.add(player)
    KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(player)

    //Start Timers
    setupTimers()
}

fun Update(){
    player.movePlayer()
    player.animatePlayer()
}

fun FixedUpdate(){
    player.playerCollisionCheck()
}





