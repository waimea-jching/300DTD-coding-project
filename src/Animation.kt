//=============================================================================================


import java.awt.*
import javax.swing.*


//=============================================================================================

class Animation(imagePaths : MutableList<String>, speed : Int, rectangleBounds: Rectangle) {
    //initializing
    private val bounds = rectangleBounds
    private val paths = imagePaths
    val animationSpeed = speed

    //frames list
    val frames = mutableListOf<ImageIcon>()

    init {
        loadImages()
    }

    private fun loadImages() {
        for (path in paths) {
            //grab image from package
            var image = ImageIcon(path).image
            //scale image
            image = image.getScaledInstance(bounds.width, bounds.height, Image.SCALE_SMOOTH)
            val imageIcon = ImageIcon(image)
            frames.add(imageIcon)
        }
    }
}