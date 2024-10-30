//=============================================================================================


import java.awt.*
import javax.swing.*


//=============================================================================================


class Display(private val displayName: String, private val displaySize: Dimension, val displayBoundary : Dimension) : JFrame(){
    init {
        setupWindow()

        // Show the display, centred on screen
        setLocationRelativeTo(null)
        isVisible = true
    }

    private fun setupWindow() {
        title = displayName
        contentPane.preferredSize = displaySize
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        isResizable = false
        layout = null

        pack()
    }
}