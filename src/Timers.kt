//=============================================================================================


import java.awt.event.ActionListener
import javax.swing.Timer


//=============================================================================================


//Timer Speeds
private const val frameRate : Int = 30 /*ticks per second*/

//Timers
private lateinit var updateTimer: Timer

fun setupTimers() {
    val updateListener = ActionListener {
        update()
    }
    updateTimer = Timer((1000/ frameRate), updateListener)
    updateTimer.start()
}

fun stopTimers() {
    updateTimer.stop()
}
