//=============================================================================================


import java.awt.event.ActionListener
import javax.swing.ImageIcon
import javax.swing.Timer


//=============================================================================================


class Animator() {
    //animation class
    private lateinit var currentAnimation : Animation
    private var currentAnimationFrame : ImageIcon = ImageIcon()
    private var animationIndex: Int = 0

    //other
    private var animationSpeed : Int = 0
    var loop : Boolean = true

    private lateinit var animationTimer: Timer
    private val animationListener : ActionListener = ActionListener {updateAnimation()}

    init {
        animationTimer = Timer(0, animationListener)
    }

    private fun updateAnimation() {
        //playing through selected animations frames either
        // in a loop or just once
        if (loop) {
            if (animationIndex >= currentAnimation.frames.count()) animationIndex = 0
            currentAnimationFrame = currentAnimation.frames[animationIndex]
            animationIndex++
        }
        else {
            if (animationIndex == currentAnimation.frames.count()-1) stopAnimation()
            currentAnimationFrame = currentAnimation.frames[animationIndex]
            animationIndex++
        }
    }

    fun getCurrentFrame(): ImageIcon {
        return currentAnimationFrame
    }

    fun setAnimation(animation: Animation) {
        currentAnimation = animation
        animationSpeed = animation.animationSpeed
    }

    fun getCurrentAnimation() : Animation {
        return currentAnimation
    }

    fun playAnimation() {
        //if looping turned off make sure to start at beginning frame
        // in case animation index is set to something else
        if (!loop) animationIndex = 0

        if (animationTimer.isRunning) stopAnimation()
        animationTimer.removeActionListener(animationListener)

        animationTimer = Timer((1000/ animationSpeed), animationListener)
        updateAnimation()
        animationTimer.start()
    }

    fun stopAnimation() {
        animationTimer.stop()
    }
}