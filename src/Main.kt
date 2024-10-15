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


fun main(){
    Awake()
}

lateinit var label : JLabel
var move : Int = 0

fun Awake(){
    // Flat, Dark look-and-feel
    FlatDarkLaf.setup()

    // Create the UI
    SetupFrame()
    SetupTimers()

    label = JLabel("ahhhhhh", SwingConstants.CENTER)
    label.bounds = Rectangle(30, 30, 240, 50)
    display.title = "ahhhhhhh"
    display.add(label)
}

fun Update(){
    move ++
    label.bounds = Rectangle(move, 30, 240, 50)
    display.add(label)
}




