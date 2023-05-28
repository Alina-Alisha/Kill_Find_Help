import com.soywiz.klock.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import behavior.*
import com.soywiz.korim.bitmap.*

class PlayerManager(private val scene: SceneContainer, private val player: PlayerCharacter, private val heart: Bitmap) {
    companion object {
        var numOfOpenChest: Int = 1
        var playerHealth: Int = 0
    }
    fun update() {
        player.addUpdater { time ->
            val scale = 16.milliseconds / time
            val disp = 2 * scale
            val keys = scene.views.input.keys
            val input = scene.views.input

            player.handleKeys(keys, disp)
            player.mouseEvents(input)

            playerHealth = player.health.toInt()
        }
    }
}
