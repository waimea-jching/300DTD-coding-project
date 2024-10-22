//=============================================================================================


import com.formdev.flatlaf.FlatDarkLaf
import com.formdev.flatlaf.FlatLightLaf
import java.awt.*
import java.awt.event.*
import javax.swing.*


//=============================================================================================


class Display(displayName: String, displaySize: Dimension) : JFrame(){
    private val name = displayName
    private val size = displaySize

    init {
        setupWindow()

        // Show the app, centred on screen
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