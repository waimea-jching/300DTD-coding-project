//=============================================================================================


import java.awt.event.ActionListener
import javax.swing.Timer
import javax.swing.*


//=============================================================================================


//Variables
private val frameRate : Int = 30 /*how many times per second*/
private val physicsTick : Int = frameRate * 2/*how many times per second*/

//Timers
private lateinit var updateTimer: Timer
private lateinit var physicsTimer: Timer

fun SetupTimers() {
    val physics = ActionListener {
        PhysicsUpdate()
    }
    physicsTimer = Timer((1000/ physicsTick), physics)
    physicsTimer.start()

    val update = ActionListener {
        Update()
    }
    updateTimer = Timer((1000/ frameRate), update)
    updateTimer.start()
}
