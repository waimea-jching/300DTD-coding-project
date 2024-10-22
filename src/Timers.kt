//=============================================================================================


import java.awt.event.ActionListener
import javax.swing.Timer


//=============================================================================================


//Timer Speeds
private val frameRate : Int = 30 /*ticks per second*/
private val fixedUpdateTick : Int = frameRate * 2/*ticks per second*/

//Timer Variables
private lateinit var updateTimer: Timer
private lateinit var fixedUpdateTimer: Timer

fun SetupTimers() {
    val fixedUpdateListener = ActionListener {
        FixedUpdate()
    }
    fixedUpdateTimer = Timer((1000/ fixedUpdateTick), fixedUpdateListener)
    fixedUpdateTimer.start()

    val updateListener = ActionListener {
        Update()
    }
    updateTimer = Timer((1000/ frameRate), updateListener)
    updateTimer.start()
}