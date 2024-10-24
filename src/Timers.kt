//=============================================================================================


import java.awt.event.ActionListener
import javax.swing.Timer


//=============================================================================================


//Timer Speeds
private const val frameRate : Int = 60 /*ticks per second*/
private const val fixedUpdateTick : Int = 40/*ticks per second*/

//Timers
private lateinit var updateTimer: Timer
private lateinit var fixedUpdateTimer: Timer

fun setupTimers() {
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

fun stopTimers() {
    updateTimer.stop()
    fixedUpdateTimer.stop()
}
