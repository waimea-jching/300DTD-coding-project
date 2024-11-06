//=============================================================================================


import javax.swing.JLabel


//=============================================================================================

class Room(private val gameDisplay: Display) {
    val doors = mutableListOf<Door>()
    val enemys = mutableListOf<Enemy>()

    fun loadRoom() {
        for (door in doors) {
            gameDisplay.background.add(door)
        }

        for (enemy in enemys) {
            gameDisplay.background.add(enemy)
        }
    }

    fun deloadRoom() {
        for (enemy in enemys) {
            enemy.enemyAnimator.stopAnimation()
            enemy.enemyCollider.destroyCollider()
            gameDisplay.background.remove(enemy)
        }

        for (door in doors) {
            gameDisplay.background.remove(door)
        }
    }

    fun updateRoom() {
        for (enemy in enemys) {
            enemy.enemyCollisionCheck()
            enemy.checkForPlayer()
            enemy.animateEnemy()
            enemy.moveEnemy()
        }
    }

    fun addDoor(door: Door) {
        doors.add(door)
    }

    fun addEnemy(player : Player) {
        val enemy = Enemy(gameDisplay, player)
        enemys.add(enemy)
    }
}