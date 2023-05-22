package logicOfBehavior

import com.soywiz.klock.*
import com.soywiz.korge.view.*

class ChestAnimate(
    idleAnimation: SpriteAnimation,
    val openAnimation: SpriteAnimation,
    val numOfLoot: Int
) : Sprite(idleAnimation) {
    var wasOpen = false
    private fun open() {
        if (!wasOpen) {
            playAnimation(spriteAnimation = openAnimation, spriteDisplayTime = 200.milliseconds)
            wasOpen = true
        }
    }

    fun update(player: PlayerCharacter, loot: LootAnimate) {
        this.onCollision({it == player && player.isOpeningChest}) {
            open()
            loot.update(this as ChestAnimate)
            loot.addUpdater { loot.animate(it) }
        }
    }
}
