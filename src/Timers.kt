//=============================================================================================


import java.awt.event.ActionListener
import javax.swing.Timer
import javax.swing.*


//=============================================================================================


//Variables
private val frameRatePS : Int = 30

//Timers
private lateinit var updateTimer: Timer

fun SetupTimers() {
    val update = ActionListener {
        Update()
    }
    updateTimer = Timer((1000/ frameRatePS), update)
    updateTimer.start()
}
