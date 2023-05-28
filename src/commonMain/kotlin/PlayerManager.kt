import com.soywiz.klock.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import behavior.*
import com.soywiz.korim.bitmap.*

class PlayerManager(private val scene: SceneContainer, private val player: PlayerCharacter, private val heart: Bitmap) {
    private fun Container.hearts(
        baseValue: Int,
        icon: Bitmap = heart,
        space: Double = 0.0,
        block: Hearts.() -> Unit = {}
    ) {
        val hearts = Hearts(baseValue, icon, space)
        this.addChild(hearts)
        hearts.block()
    }
    fun update() {
        var playerHealth = player.health.toInt()
        var numOfOpenChest = 1

        player.addUpdater { time ->
            val scale = 16.milliseconds / time
            val disp = 2 * scale
            val keys = scene.views.input.keys
            val input = scene.views.input

            player.handleKeys(keys, disp)
            player.mouseEvents(input)

            playerHealth = player.health.toInt()
        }

        scene.hearts(4) {
            x = 10.0
            y = 10.0
            scene.addUpdater {
                val h = player.health.toInt()
                if (h == (playerHealth - 1))
                    this@hearts.value--
                if (player.isOpeningChest && numOfOpenChest > 0) {
                    this@hearts.value++
                    numOfOpenChest--
                }
            }
        }
    }
}
