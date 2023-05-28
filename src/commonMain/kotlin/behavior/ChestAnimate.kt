package behavior

import com.soywiz.klock.*
import com.soywiz.korge.view.*

class ChestAnimate(
    idleAnimation: SpriteAnimation,
    private val openAnimation: SpriteAnimation,
    private val numOfLoot: Int
) : Sprite(idleAnimation) {
    var wasOpen = false
    var plusLives = numOfLoot
    private fun open() {
        if (!wasOpen) {
            playAnimation(spriteAnimation = openAnimation, spriteDisplayTime = 200.milliseconds)
            wasOpen = true
        }
    }

    fun update(player: PlayerCharacter, loot: LootAnimate) {
        this.onCollision({ it == player && player.isOpeningChest }) {
            open()
            loot.update(this as ChestAnimate)
            loot.addUpdater { loot.animate(it) }
            if (plusLives > 0) {
                player.health += 1
                plusLives -= 1
            }
        }
    }
}
