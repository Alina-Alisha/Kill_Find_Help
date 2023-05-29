package screens

import MyModule
import com.soywiz.korge.input.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.ui.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import com.soywiz.korim.font.*
import com.soywiz.korim.format.*
import com.soywiz.korim.text.*
import com.soywiz.korio.file.std.*
import kotlin.random.*

data class ButtonsAndLevels(
    val button: UIButton,
    val level: Int,
    val event: Int
)

fun rand(start: Int, end: Int): Int {
    require(!(start > end || end - start + 1 > Int.MAX_VALUE)) { "Illegal Argument" }
    return Random(System.nanoTime()).nextInt(end - start + 1) + start
}

fun chooseSpace(availableSpaces: MutableMap<Pair<Double, Double>, Int>): Pair<Double, Double> {
    val h = rand(0, 5)
    val y = 89.0 + 36.0 * h
    val w = rand(0, 1)
    val x = if (w == 0) 180.0 else 420.0
    return if (availableSpaces[Pair(x, y)] == 0) {
        availableSpaces[Pair(x, y)] = 1
        Pair(x, y)
    } else chooseSpace(availableSpaces)
}

class GameMenu : Scene() {
    override suspend fun SContainer.sceneInit() {
        val font = resourcesVfs["mr_countryhouseg_0.ttf"].readTtfFont(preload = false)
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
        )

        var titleText = text("Available jobs", 30.0, Colors.WHITE, font) {
            position(views.virtualWidth / 2 - 70, views.virtualHeight / 2 - 157)
        }

        val level1Button = uiButton(32.0, 32.0) {
            text = "kill 2 monsters        "
            uiSkin = UISkin {
                val colorTransform = ColorTransform(0.48, 0.83, 0.66)
                this.buttonBackColor = this.buttonBackColor.transform(colorTransform)
                this.textColor = Colors.BLACK
                this.textFont = font
                this.textSize = 20.0
                this.buttonTextAlignment = TextAlignment.TOP_RIGHT
            }
            onClick {
                println(1)
                sceneContainer.changeTo<GameView>()
            }
        }


        val level2Button = uiButton(32.0, 32.0) {
            text = "kill 4 monsters        "
            uiSkin = UISkin {
                val colorTransform = ColorTransform(0.48, 0.83, 0.66)
                this.buttonBackColor = this.buttonBackColor.transform(colorTransform)
                this.textColor = Colors.BLACK
                this.textFont = font
                this.textSize = 20.0
                this.buttonTextAlignment = TextAlignment.TOP_RIGHT
            }
            visible = false
            onClick {
                MyModule.passEvent = false
                MyModule.event = 2
                MyModule.health = 5.0
                MyModule.numOfLoot = 0
                println(2)
                sceneContainer.changeTo<GameView>()
            }
        }

        val level3Button = uiButton(32.0, 32.0) {
            text = "kill 6 monsters        "
            textColor = Colors.BLACK
            textFont = font
            textSize = 20.0
            buttonBackColor = Colors["#bfa56a"]
            buttonTextAlignment = TextAlignment.TOP_RIGHT
            visible = false
            onClick {
                MyModule.passEvent = false
                MyModule.event = 3
                MyModule.health = 5.0
                MyModule.numOfLoot = 2
                println(3)
                sceneContainer.changeTo<GameView>()
            }
        }

        val level4Button = uiButton(32.0, 32.0) {
            text = "kill 7 monsters        "
            uiSkin = UISkin {
                val colorTransform = ColorTransform(0.48, 0.83, 0.66)
                this.buttonBackColor = this.buttonBackColor.transform(colorTransform)
                this.textColor = Colors.BLACK
                this.textFont = font
                this.textSize = 20.0
                this.buttonTextAlignment = TextAlignment.TOP_RIGHT
                visible = false
            }
            onClick {
                MyModule.passEvent = false
                MyModule.event = 4
                MyModule.health = 7.0
                MyModule.numOfLoot = 1
                println(4)
                sceneContainer.changeTo<GameView>()
            }
        }

        val level5Button = uiButton(32.0, 32.0) {
            text = "kill 7 monsters        "
            uiSkin = UISkin {
                val colorTransform = ColorTransform(0.48, 0.83, 0.66)
                this.buttonBackColor = this.buttonBackColor.transform(colorTransform)
                this.textColor = Colors.BLACK
                this.textFont = font
                this.textSize = 20.0
                this.buttonTextAlignment = TextAlignment.TOP_RIGHT
                visible = false
            }
            onClick {
                MyModule.passEvent = false
                MyModule.event = 5
                MyModule.health = 7.0
                MyModule.numOfLoot = 1
                println(5)
                sceneContainer.changeTo<GameView>()
            }
        }


        val assignments = listOf(
            ButtonsAndLevels(level1Button, 1, 1),
            ButtonsAndLevels(level2Button, 2, 2),
            ButtonsAndLevels(level3Button, 2, 3),
            ButtonsAndLevels(level4Button, 3, 4),
            ButtonsAndLevels(level5Button, 3, 5)
        )

        assignments.filter { it.event == MyModule.event }.onEach {
            if (MyModule.passEvent) {
                availableSpaces[Pair(it.button.x, it.button.y)] = 0
                it.button.visible = false
            }
        }

        assignments.filter { it.level == MyModule.level }.onEach {
            val a = chooseSpace(availableSpaces)
            it.button.addUpdater {
                visible = true
                position(a.first, a.second)
            }
        }.any()


        var exitButton = uiButton(32.0, 32.0) {
            text = "Exit        "
            textColor = Colors.BLACK
            textFont = font
            textSize = 20.0
            buttonTextAlignment = TextAlignment.TOP_RIGHT
            position(440, 269)
            onClick {
                views.gameWindow.close()
            }
        }
    }
}
