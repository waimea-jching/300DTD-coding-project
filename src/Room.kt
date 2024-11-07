//=============================================================================================


import javax.swing.JLabel


//=============================================================================================

class Room(private val gameDisplay: Display) {
    val enemys = mutableListOf<Enemy>()

    fun loadRoom() {

        for (enemy in enemys) {
            gameDisplay.background.add(enemy)
        }
    }

    fun deloadRoom() {
        for (enemy in enemys) {
            enemy.isVisible = false
            enemy.enemyAnimator.stopAnimation()
            enemy.enemyCollider.destroyCollider()
            gameDisplay.background.remove(enemy)
            enemy.isDead = true
        }
    }

    fun updateRoom() {
        for (enemy in enemys) {
            if (!enemy.isDead) {
                enemy.enemyCollisionCheck()
                enemy.checkForPlayer()
                enemy.animateEnemy()
                enemy.moveEnemy()
            }
            else {
                enemys.remove(enemy)
            }
        }
    }

    fun addEnemy(player : Player) {
        val enemy = Enemy(gameDisplay, player)
        enemys.add(enemy)
    }
}