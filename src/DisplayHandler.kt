//=============================================================================================


import java.awt.*
import javax.swing.*


//=============================================================================================


class Display(displayName: String, displaySize: Dimension) : JFrame(){
    //initializing
    private val name = displayName
    private val size = displaySize

    init {
        setupWindow()

        // Show the display, centred on screen
        setLocationRelativeTo(null)
        isVisible = true
    }

    private fun setupWindow() {
        title = name
        contentPane.preferredSize = size
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        isResizable = false
        layout = null

        pack()
    }
}