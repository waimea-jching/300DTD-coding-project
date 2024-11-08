//=============================================================================================


import com.formdev.flatlaf.FlatDarkLaf
import com.formdev.flatlaf.FlatLaf
import java.awt.*
import java.awt.event.ActionListener
import javax.swing.ImageIcon
import javax.swing.JLabel
import javax.swing.SwingConstants
import javax.swing.Timer
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

//UI
lateinit var playerHealthLabel : JLabel
lateinit var waveLabel : JLabel
lateinit var gameOverLabel : JLabel
lateinit var highScorelabel : JLabel
var highScore : Int = 0

//Enemys
val enemys = mutableListOf<Enemy>()
var waveNumber : Int = 1
val waveTimerListener : ActionListener = ActionListener {nextWave()}
val waveTimer : Timer = Timer(800, waveTimerListener)

//Player Instance
lateinit var player : Player

fun main(){
    startGame()
}

fun startGame(){
    //Flat, Dark look
    FlatDarkLaf.setup()
    val flatLafFont = FlatLaf.getPreferredFontFamily()
    val baseFont = Font(flatLafFont, Font.PLAIN, 20)
    val bigFont = Font(flatLafFont, Font.PLAIN, 100)

    //Load background Icon
    var backgroundImage = ImageIcon("src/images/gridBackground.png").image
    backgroundImage = backgroundImage.getScaledInstance(800, 600, Image.SCALE_SMOOTH)
    val backgroundIcon = ImageIcon(backgroundImage)

    //Create displays
    gameDisplay = Display("Dungeon Knight", Dimension(800, 600), Dimension(800,600))

    playerHealthLabel = JLabel("Health: 100/100")
    playerHealthLabel.bounds = Rectangle(15, 565, 200, 20)
    playerHealthLabel.font = baseFont
    gameDisplay.add(playerHealthLabel)

    waveLabel = JLabel("Wave: 1")
    waveLabel.bounds = Rectangle(15, 530, 200, 20)
    waveLabel.font = baseFont
    gameDisplay.add(waveLabel)

    gameOverLabel = JLabel("Game Over", SwingConstants.CENTER)
    gameOverLabel.bounds = Rectangle(0, -80, 800, 600)
    gameOverLabel.font = bigFont
    gameOverLabel.isVisible = false
    gameDisplay.add(gameOverLabel)

    highScorelabel = JLabel("High Score", SwingConstants.CENTER)
    highScorelabel.bounds = Rectangle(0, 0, 800, 600)
    highScorelabel.font = baseFont
    highScorelabel.isVisible = false
    gameDisplay.add(highScorelabel)

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
    playerHealthLabel.text = "Health: ${player.health}/100"
    waveLabel.text = "Wave: $waveNumber"

    if (player.isDead) playerDied()
    player.playerCollisionCheck()
    player.animatePlayer()
    player.movePlayer()

    for (enemy in enemys) {
        if (!enemy.isDead) {
            enemy.enemyCollisionCheck()
            enemy.checkForPlayer()
            enemy.animateEnemy()
            enemy.moveEnemy()
        }
    }

    var enemyCounter = 0
    for (enemy in enemys) {
        if (enemy.isDead){
            enemyCounter ++
        }
    }
    if (enemyCounter == enemys.count()){
        waveTimer.start()
    }
}

fun addEnemys(){
    val numberOfEnemys = Random.nextInt(waveNumber ,waveNumber + 2)
    repeat(numberOfEnemys) {
        enemys.add(Enemy(gameDisplay, player))
    }
}

fun nextWave(){
    waveTimer.stop()
    waveNumber++

    for (enemy in enemys) {
        background.remove(enemy)
    }
    enemys.clear()

    addEnemys()
}

fun playerDied() {
    playerHealthLabel.text = "Health: ${player.health}/100"
    updateTimer.stop()
    for (enemy in enemys){
        enemy.attackTimer.stop()
    }
    if (waveNumber >= highScore){
        highScore = waveNumber
    }
    highScorelabel.text = "HighScore: $highScore"
    highScorelabel.isVisible = true
    gameOverLabel.isVisible = true
}






