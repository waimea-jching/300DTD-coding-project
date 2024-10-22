//=============================================================================================


import java.awt.*
import java.awt.event.*
import javax.swing.*


//=============================================================================================

class Animation(imagePaths : MutableList<String>) {
    val images = mutableListOf<ImageIcon>()
    private val paths : MutableList<String> = imagePaths

    init {
        loadImages()
    }

    private fun loadImages() {
        for (path in paths) {
            val image = ImageIcon(path).image
            val imageIcon = ImageIcon(image)
            images.add(imageIcon)


//            var dogImage = ImageIcon("src/images/dog.jpeg").image
//            dogImage = dogImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH)
//            dogImageIcon = ImageIcon(dogImage)
        }
    }
}