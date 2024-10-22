//=============================================================================================


import java.awt.*
import java.awt.event.*
import javax.swing.*


//=============================================================================================

class Animation(imagePaths : MutableList<String>, rectangleBounds: Rectangle) {
    val bounds = rectangleBounds
    val images = mutableListOf<ImageIcon>()
    private val paths = imagePaths

    init {
        loadImages()
    }

    private fun loadImages() {
        for (path in paths) {
            var image = ImageIcon(path).image
            image = image.getScaledInstance(bounds.width, bounds.height, Image.SCALE_SMOOTH)
            val imageIcon = ImageIcon(image)
            images.add(imageIcon)


//            var dogImage = ImageIcon("src/images/dog.jpeg").image
//            dogImage = dogImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH)
//            dogImageIcon = ImageIcon(dogImage)
        }
    }
}