//=============================================================================================


import com.formdev.flatlaf.FlatDarkLaf
import com.formdev.flatlaf.FlatLightLaf
import java.awt.*
import java.awt.event.*
import java.io.Console
import javax.swing.*


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
    // Flat, Dark look-and-feel
    FlatDarkLaf.setup()

    // Create the display
    SetupDisplay()
    SetupTimers()

    player = Player(display)
    KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(player)
}

fun Update(){
    player.playerCollisionCheck()
    player.movePlayer()
}




