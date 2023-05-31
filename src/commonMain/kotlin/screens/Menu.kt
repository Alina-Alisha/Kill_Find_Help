package screens

import com.soywiz.korge.input.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.ui.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import com.soywiz.korim.font.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*
import screens.*

class Menu() : Scene() {
    override suspend fun SContainer.sceneInit() {
        val x0 = sceneContainer.width / 2
        val y0 = sceneContainer.height / 2
        var img = image(resourcesVfs["bckgr.png"].readBitmap(), 0.5, 0.5) {
            x = x0
            y = y0
        }
        val font = resourcesVfs["mr_countryhouseg_0.ttf"].readTtfFont(preload = false)

        var gameNameText = text("Kill (us)", 40.0, Colors.BLACK, font) {
            position(views.virtualWidth / 2 - 50, views.virtualHeight / 2 - 128)
        }


        var playButton = uiButton(200.0, 40.0) {
            text = "Play"
            //buttonBackColor = Colors["#25bf3e"]
            uiSkin = UISkin {
                val colorTransform = ColorTransform(0.48, 0.83, 0.66)
                this.uiSkinBitmap = this.uiSkinBitmap.withColorTransform(colorTransform)
                this.buttonBackColor = this.buttonBackColor.transform(colorTransform)
                this.textSize = 30.0
                this.textFont = font
            }
            position(views.virtualWidth / 2 - 100, views.virtualHeight / 2 - 64)
            onClick {
                sceneContainer.changeTo<ChooseYourFighter>()
            }
        }
        var exitButton = uiButton(200.0, 40.0) {
            text = "Exit"
            uiSkin = UISkin {
                val colorTransform = ColorTransform(0.48, 0.83, 0.66)
                this.uiSkinBitmap = this.uiSkinBitmap.withColorTransform(colorTransform)
                this.buttonBackColor = this.buttonBackColor.transform(colorTransform)
                this.textSize = 30.0
                this.textFont = font
            }
            position(views.virtualWidth / 2 - 100, views.virtualHeight / 2)
            onClick {
                views.gameWindow.close()
            }
        }
    }

}
