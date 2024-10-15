//=============================================================================================


import java.awt.event.ActionListener
import javax.swing.Timer
import javax.swing.*


//=============================================================================================


//Variables
private val frameRatePS : Int = 1
var runningTime : Float = 0f

//Timers
private lateinit var updateTimer: Timer
private lateinit var time: Timer

fun SetupTimers() {
    val update = ActionListener {
        Update()
    }
    updateTimer = Timer((1000/ frameRatePS), update)
    updateTimer.start()


    val timeDelta = ActionListener {
        runningTime += 0.001f
    }
    time = Timer(1, update)
    time.start()
}
