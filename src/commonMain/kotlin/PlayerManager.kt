import com.soywiz.klock.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*

class PlayerManager(private val scene: SceneContainer, private val player: PlayerCharacter) {
    fun update() {
        player.addUpdater { time ->
            val scale = 16.milliseconds / time
            val disp = 2 * scale
            val keys = scene.views.input.keys
            val input = scene.views.input

            player.handleKeys(keys, disp)
            player.mouseEvents(input, disp)
        }
    }
}
