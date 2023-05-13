import com.soywiz.korge.input.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.ui.*
import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*
import com.soywiz.korim.color.*
import com.soywiz.korim.font.*
import com.soywiz.korim.format.*
import com.soywiz.korim.text.*
import com.soywiz.korio.compression.*
import com.soywiz.korio.compression.deflate.*
import com.soywiz.korio.file.std.*
import com.soywiz.korio.stream.*
import com.soywiz.krypto.encoding.*
import kotlin.random.*

fun chooseSpace(availableSpaces: MutableMap<Pair<Double, Double>, Int>): Pair<Double, Double> {
    val h = rand(0, 5)
    val y = 89.0 + 36.0 * h
    val w = rand(0, 1)
    val x = if (w == 0) 180.0 else 420.0
    return if (availableSpaces[Pair(x, y)] == 0) {
        availableSpaces[Pair(x, y)] = 1
        Pair(x, y)
    }
    else chooseSpace(availableSpaces)
}

class GameMenu : Scene() {
    override suspend fun SContainer.sceneInit() {
        val font = resourcesVfs["mr_countryhouseg_0.ttf"].readTtfFont(preload = false)
        //val paper = resourcesVfs["paper1 (2).png"].readBitmap().toBMP32IfRequired()
        val x0 = sceneContainer.width / 2
        val y0 = sceneContainer.height / 2
        var img = image(resourcesVfs["new_board.png"].readBitmap(), 0.5, 0.5) {
            x = x0
            y = y0
        }

        val availableSpaces = mutableMapOf(
            Pair(180.0, 89.0) to 0,
            Pair(180.0, 125.0) to 0,
            Pair(180.0, 161.0) to 0,
            Pair(180.0, 197.0) to 0,
            Pair(180.0, 233.0) to 0,
            Pair(180.0, 269.0) to 0,

            Pair(420.0, 89.0) to 0,
            Pair(420.0, 125.0) to 0,
            Pair(420.0, 161.0) to 0,
            Pair(420.0, 197.0) to 0,
            Pair(420.0, 233.0) to 0,
            Pair(420.0, 269.0) to 1
        )

        var titleText = text("Available jobs", 30.0, Colors.WHITE, font) {
            position(views.virtualWidth / 2 - 70, views.virtualHeight / 2 - 157)
        }
        var textForButton = "kill 2 monsters        "
        val level1Button = uiButton(32.0, 32.0) {
            text = "kill 2 monsters        "
            //uiSkinBitmap = paper
            uiSkin = UISkin {
                val colorTransform = ColorTransform(0.48, 0.83, 0.66)
                this.buttonBackColor = this.buttonBackColor.transform(colorTransform)
                this.textColor = Colors.BLACK
                this.textFont = font
                this.textSize = 20.0
                this.buttonTextAlignment = TextAlignment.TOP_RIGHT
            }
            //position(c.first, c.second)
            onClick {
                sceneContainer.changeTo<Play>()
            }
        }

//        var level2Button = uiButton(32.0, 32.0) {
//            text = "kill 4 monsters        "
//            textColor = Colors.BLACK
//            textFont = font
//            textSize = 20.0
//            buttonBackColor = Colors["#bfa56a"]
//            buttonTextAlignment = TextAlignment.TOP_RIGHT
//            //uiSkinBitmap = paper
//            //position(c.first, c.second)
//            onClick {
//                sceneContainer.changeTo<Play>()
//            }
//        }

        if (MyModule.level == 1) {
            val c = chooseSpace(availableSpaces)
            level1Button.addUpdater { position(c.first, c.second) }
        }
        else if (MyModule.level == 2) {
            availableSpaces[Pair(level1Button.x, level1Button.y)] = 0
            val c = chooseSpace(availableSpaces)
            level1Button.text = "kill 4 monsters        "
            level1Button.addUpdater { position(c.first, c.second) }
        }



//        if (MyModule.level == 2) {
//            val c = chooseSpace(availableSpaces)
//            level2Button.addUpdater { position(c.first, c.second) }
//        }
//        else availableSpaces[Pair(level2Button.x, level2Button.y)] = 0

        var exitButton = uiButton(32.0, 32.0) {
            text = "Exit        "
            textColor = Colors.BLACK
            textFont = font
            textSize = 20.0
            //uiSkinBitmap = paper
            //buttonBackColor = Colors["#bfa56a"]
            buttonTextAlignment = TextAlignment.TOP_RIGHT
            position(440, 269)
            onClick {
                views.gameWindow.close()
            }
        }
    }
}
