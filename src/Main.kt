//=============================================================================================


import com.formdev.flatlaf.FlatDarkLaf
import java.awt.*


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


//Display Object
lateinit var display : Display
lateinit var player : Player

fun main(){
    Awake()
}

fun Awake(){
    // Flat, Dark look
    FlatDarkLaf.setup()

    // Create the display
    display = Display("Tomb Run", Dimension(800, 600))

    //Instantiate Player & Allow to Read Input
    player = Player(display)
    KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(player)

    //Start Timers
    SetupTimers()
}

fun Update(){
    player.movePlayer()
}

fun FixedUpdate(){
    player.playerCollisionCheck()
    player.animatePlayer()
}





