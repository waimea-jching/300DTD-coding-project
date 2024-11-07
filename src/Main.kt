//=============================================================================================


import com.formdev.flatlaf.FlatDarkLaf
import java.awt.*
import javax.swing.ImageIcon
import javax.swing.JLabel
import kotlin.random.Random


//=============================================================================================


/**
 * ------------------------------------------------------------------------
 * Dungeon Knight
 * Level 3 programming project
 *
 * by Josiah Ching
 *
 * BRIEF PROJECT DESCRIPTION HERE
 * BRIEF PROJECT DESCRIPTION HERE
 * BRIEF PROJECT DESCRIPTION HERE
 * ------------------------------------------------------------------------
 */

//Displays
lateinit var gameDisplay : Display
lateinit var background : JLabel

//Enemys
val enemys = mutableListOf<Enemy>()
var waveNumber = 1

//Player Instance
lateinit var player : Player

fun main(){
    awake()
}

fun awake(){
    //Flat, Dark look
    FlatDarkLaf.setup()

    //Load background Icon
    var backgroundImage = ImageIcon("src/images/gridBackground.png").image
    backgroundImage = backgroundImage.getScaledInstance(800, 600, Image.SCALE_SMOOTH)
    val backgroundIcon = ImageIcon(backgroundImage)

    //Create displays
    gameDisplay = Display("Dungeon Knight", Dimension(800, 600), Dimension(800,600))

    //Create Background
    background = JLabel()
    background.bounds = Rectangle(0, 0, 800, 600)
    background.icon = backgroundIcon
    gameDisplay.background = background
    gameDisplay.add(background)

    //Instantiate Player & Allow to Read Input
    player = Player(gameDisplay)
    background.add(player)

    addEnemys()

    //Start Timers & enable input
    KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(player)
    setupTimers()
}

fun update(){
    player.playerCollisionCheck()
    player.animatePlayer()
    player.movePlayer()

    var enemyCounter = 0
    for (enemy in enemys) {
        if (!enemy.isDead) {
            enemy.enemyCollisionCheck()
            enemy.checkForPlayer()
            enemy.animateEnemy()
            enemy.moveEnemy()
        }
        else{
            enemyCounter ++
        }
    }
    if (enemyCounter == enemys.count()){
        nextWave()
    }

    if (player.isDead) playerDied()
}

fun addEnemys(){
    val numberOfEnemys = Random.nextInt(waveNumber ,waveNumber + 2)
    repeat(numberOfEnemys){
        enemys.add(Enemy(gameDisplay, player))
    }
}

fun nextWave(){
    waveNumber++
    enemys.clear()
    addEnemys()
}

fun playerDied() {
    updateTimer.stop()
}






