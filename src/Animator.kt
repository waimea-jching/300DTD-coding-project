//=============================================================================================


import java.awt.event.ActionListener
import javax.swing.Icon
import javax.swing.ImageIcon
import javax.swing.JLabel
import javax.swing.Timer


//=============================================================================================


class Animator() {
    private lateinit var currentAnimation : Animation
    private var currentAnimationFrame : ImageIcon = ImageIcon()
    private var animationSpeed : Int = 0

    private lateinit var animationTimer: Timer
    private var animationIndex: Int = 0

    val animationListener : ActionListener = ActionListener {updateAnimation()}

    private fun updateAnimation() {
        if (animationIndex >= currentAnimation.images.count()) animationIndex = 0

        currentAnimationFrame = currentAnimation.images[animationIndex]

        animationIndex++
    }

    fun getCurrentAnimationFrame(): ImageIcon {
        return currentAnimationFrame
    }

    fun setAnimation(animation: Animation) {
        currentAnimation = animation
    }

    fun setAnimationSpeed(speed : Int) {
        animationSpeed = speed
        animationTimer = Timer((1000/ animationSpeed), animationListener)
    }

    fun playAnimation() {
        animationTimer = Timer((1000/ animationSpeed), animationListener)
        animationTimer.start()
    }

    fun stopAnimation() {
        animationTimer.stop()
    }
}