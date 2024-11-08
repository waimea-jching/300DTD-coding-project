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
 * ------------------------------------------------------------------------
 */

//Displays
lateinit var gameDisplay : Display
lateinit var background : JLabel

//UI & UI Variables
lateinit var playerHealthLabel : JLabel
lateinit var wasdLabel : JLabel
lateinit var spaceLabel : JLabel
lateinit var waveLabel : JLabel
lateinit var gameOverLabel : JLabel
lateinit var highScorelabel : JLabel
var highScore : Int = 0

//Enemies
val enemies = mutableListOf<Enemy>()
var waveNumber : Int = 1
val waveTimerListener : ActionListener = ActionListener {nextWave()}
val waveTimer : Timer = Timer(800, waveTimerListener)

//Player Instance
lateinit var player : Player

//Fonts
val flatLafFont = FlatLaf.getPreferredFontFamily()
val baseFont = Font(flatLafFont, Font.PLAIN, 20)
val bigFont = Font(flatLafFont, Font.PLAIN, 100)
val titleFont = Font(flatLafFont, Font.PLAIN, 40)

fun main(){
    startGame()
}

fun startGame(){
    //Flat, Dark look
    FlatDarkLaf.setup()

    //Load background Icon
    var backgroundImage = ImageIcon("src/images/gridBackground.png").image
    backgroundImage = backgroundImage.getScaledInstance(800, 600, Image.SCALE_SMOOTH)
    val backgroundIcon = ImageIcon(backgroundImage)

    //Create Display
    gameDisplay = Display("Dungeon Knight", Dimension(800, 600), Dimension(800,600))

    //Building UI
    playerHealthLabel = JLabel("Health: 100/100")
    playerHealthLabel.bounds = Rectangle(15, 565, 200, 20)
    playerHealthLabel.font = baseFont
    gameDisplay.add(playerHealthLabel)

    wasdLabel = JLabel("wasd - move")
    wasdLabel.bounds = Rectangle(15, 460, 200, 20)
    wasdLabel.font = baseFont
    gameDisplay.add(wasdLabel)

    spaceLabel = JLabel("space - attack")
    spaceLabel.bounds = Rectangle(15, 495, 200, 20)
    spaceLabel.font = baseFont
    gameDisplay.add(spaceLabel)

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
    //Update UI
    playerHealthLabel.text = "Health: ${player.health}/100"
    waveLabel.text = "Wave: $waveNumber"

    //Update Player
    if (player.isDead) playerDied()
    player.playerCollisionCheck()
    player.animatePlayer()
    player.movePlayer()

    //Update Enemies
    for (enemy in enemies) {
        if (!enemy.isDead) {
            enemy.enemyCollisionCheck()
            enemy.checkForPlayer()
            enemy.animateEnemy()
            enemy.moveEnemy()
        }
    }
    var enemyCounter = 0
    for (enemy in enemies) {
        if (enemy.isDead){
            enemyCounter ++
        }
    }
    if (enemyCounter == enemies.count()){
        waveTimer.start()
    }
}

fun addEnemys(){
    val numberOfEnemys = Random.nextInt(waveNumber ,waveNumber + 2)
    repeat(numberOfEnemys) {
        enemies.add(Enemy(gameDisplay, player))
    }
}

fun nextWave(){
    waveTimer.stop()
    waveNumber++

    for (enemy in enemies) {
        background.remove(enemy)
    }
    enemies.clear()

    addEnemys()
}

fun playerDied() {
    playerHealthLabel.text = "Health: ${player.health}/100"
    updateTimer.stop()
    for (enemy in enemies){
        enemy.attackTimer.stop()
    }
    if (waveNumber >= highScore){
        highScore = waveNumber
    }
    highScorelabel.text = "HighScore: $highScore"
    highScorelabel.isVisible = true
    gameOverLabel.isVisible = true
}






