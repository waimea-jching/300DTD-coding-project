//=============================================================================================


import java.awt.event.ActionListener
import javax.swing.Timer
import javax.swing.*


//=============================================================================================


//Timer Speeds
private val frameRate : Int = 30 /*ticks per second*/
private val physicsTick : Int = frameRate * 2/*ticks per second*/

//Timer Variables
private lateinit var updateTimer: Timer
private lateinit var physicsTimer: Timer

fun SetupTimers() {
    val physicsListener = ActionListener {
        PhysicsUpdate()
    }
    physicsTimer = Timer((1000/ physicsTick), physicsListener)
    physicsTimer.start()

    val updateListener = ActionListener {
        Update()
    }
    updateTimer = Timer((1000/ frameRate), updateListener)
    updateTimer.start()
}
