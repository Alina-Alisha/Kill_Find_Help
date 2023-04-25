import com.soywiz.korge.input.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.ui.*
import com.soywiz.korge.view.*
import com.soywiz.korge.view.onClick
import com.soywiz.korim.color.*

class GameMenu : Scene() {
    override suspend fun SContainer.sceneInit() {
        // set a background color

        views.clearColor = Colors["#ae6fb1"]

        // Add a text to show the name of the game
        var gameNameText = text("Levels") {
            position(views.virtualWidth / 2 - 128, views.virtualHeight / 2 - 128)
        }

        var level1Button = uiTextButton(256.0, 32.0) {
            text = "Level 1"
            position(views.virtualWidth / 2 - 128, views.virtualHeight / 2 - 64)
            onClick {
                sceneContainer.changeTo<Play>()
            }
        }
        if (MyModule.level >= 2) {
            var level2Button = uiTextButton(256.0, 32.0) {
                text = "Level 2"
                position(views.virtualWidth / 2 - 128, views.virtualHeight / 2)
                onClick {
                    sceneContainer.changeTo<Play>()
                }
            }
        }

        var exitButton = uiTextButton(256.0, 32.0) {
            text = "Exit"
            position(views.virtualWidth / 2 - 128, views.virtualHeight / 2 + 64)
            onClick {
                views.gameWindow.close()
            }
        }
    }
}
