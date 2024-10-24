//=============================================================================================


import java.awt.*
import javax.swing.*


//=============================================================================================

class Animation(private val imagePaths : MutableList<String>, val animationSpeed : Int, private val bounds: Rectangle) {
    //frames list
    val frames = mutableListOf<ImageIcon>()

    init {
        loadImages()
    }

    private fun loadImages() {
        for (path in imagePaths) {
            //grab image from package
            var image = ImageIcon(path).image
            //scale image
            image = image.getScaledInstance(bounds.width, bounds.height, Image.SCALE_SMOOTH)
            val imageIcon = ImageIcon(image)
            frames.add(imageIcon)
        }
    }
}