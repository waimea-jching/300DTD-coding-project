//=============================================================================================


import com.formdev.flatlaf.FlatDarkLaf
import com.formdev.flatlaf.FlatLaf
import java.awt.*
import javax.swing.ImageIcon
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.SwingConstants
import kotlin.concurrent.thread
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

val testEnemys = mutableListOf<Enemy>()

//Rooms
lateinit private var currentRoom : Room
lateinit private var room1 : Room

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
    gameDisplay.background = background
    gameDisplay.add(background)

    //Instantiate Player & Allow to Read Input
    player = Player(gameDisplay)
    background.add(player)

    setupRooms()
    currentRoom.loadRoom()

    //Start Timers & enable input
    KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(player)
    setupTimers()
}

fun update(){
    player.playerCollisionCheck()
    player.animatePlayer()
    player.movePlayer()

    currentRoom.updateRoom()
}

private fun setupRooms() {
    room1 = Room(gameDisplay)
    repeat(4) {
        room1.addEnemy(player)
    }

    currentRoom = room1
}





