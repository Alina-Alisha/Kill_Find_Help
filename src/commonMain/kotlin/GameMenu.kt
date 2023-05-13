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

fun chooseSpace(availableSpaces: MutableMap<Pair<Int, Int>, Int>): Pair<Int, Int> {
    var x = 0
    val h = rand(0, 5)
    val y = 89 + 36 * h
    val w = rand(0, 1)
    x = if (w == 0) 180 else 420
    if (availableSpaces[Pair(x, y)] == 0) {
        availableSpaces[Pair(x, y)] = 1
        return Pair(x, y)
    }
    else return chooseSpace(availableSpaces)
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

        var availableSpaces = mutableMapOf(
            Pair(180, 89) to 0,
            Pair(180, 125) to 0,
            Pair(180, 161) to 0,
            Pair(180, 197) to 0,
            Pair(180, 233) to 0,
            Pair(180, 269) to 0,

            Pair(420, 89) to 0,
            Pair(420, 125) to 0,
            Pair(420, 161) to 0,
            Pair(420, 197) to 0,
            Pair(420, 233) to 0,
            Pair(420, 269) to 1
        )

        var gameNameText = text("Available jobs", 30.0, Colors.WHITE, font) {
            position(views.virtualWidth / 2 - 70, views.virtualHeight / 2 - 157)
        }

        if (MyModule.level == 1) {
            val c = chooseSpace(availableSpaces)
            var level1Button = uiButton(32.0, 32.0) {
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
                position(c.first, c.second)
                onClick {
                    sceneContainer.changeTo<Play>()
                }
            }
        }

        if (MyModule.level >= 2) {
            var c = chooseSpace(availableSpaces)
            var level2Button = uiButton(32.0, 32.0) {
                text = "kill 5 monsters        "
                textColor = Colors.BLACK
                textFont = font
                textSize = 20.0
                buttonBackColor = Colors["#bfa56a"]
                buttonTextAlignment = TextAlignment.TOP_RIGHT
                //uiSkinBitmap = paper
                position(c.first, c.second)
                onClick {
                    sceneContainer.changeTo<Play>()
                }
            }
        }

        var exitButton = uiButton(32.0, 32.0) {
            text = "Exit        "
            textColor = Colors.BLACK
            textFont = font
            textSize = 20.0
            //uiSkinBitmap = paper
            //buttonBackColor = Colors["#bfa56a"]
            buttonTextAlignment = TextAlignment.TOP_RIGHT
            position(440, views.virtualHeight / 2 + 100)
            onClick {
                views.gameWindow.close()
            }
        }
    }
}
