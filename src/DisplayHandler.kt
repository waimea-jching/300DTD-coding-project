//=============================================================================================


import com.formdev.flatlaf.FlatDarkLaf
import com.formdev.flatlaf.FlatLightLaf
import java.awt.*
import java.awt.event.*
import javax.swing.*


//=============================================================================================


class Display : JFrame(){
    //Configuration Settings
    private val displayName: String = "Tomb Run"
    private val displaySize : Dimension = Dimension(800, 600)

    init {
        setupWindow()

        // Show the app, centred on screen
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
//
//    private fun buildUI() {
//        val baseFont = Font(Font.SANS_SERIF, Font.PLAIN, 20)
//
//        exampleLabel = JLabel("Hello, World!", SwingConstants.CENTER)
//        exampleLabel.bounds = Rectangle(30, 30, 240, 40)
//        exampleLabel.font = baseFont
//        add(exampleLabel)
//
//        exampleButton = JButton("Click Me")
//        exampleButton.bounds = Rectangle(30,100,240,40)
//        exampleButton.font = baseFont
//        exampleButton.addActionListener(this)
//        add(exampleButton)
//    }
}

fun SetupDisplay() {
    display = Display()
}



//=============================================================================================


