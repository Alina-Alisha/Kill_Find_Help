import com.soywiz.klock.*
import com.soywiz.korge.view.*

class ChestAnimate(
    idleAnimation: SpriteAnimation,
    numOfLoot: Int
) : Sprite(idleAnimation) {
    var wasOpen = false
    val numOfLoot = numOfLoot
    fun open(openAnimation: SpriteAnimation) {
        if (!wasOpen) {
            playAnimation(spriteAnimation = openAnimation, spriteDisplayTime = 200.milliseconds)
            wasOpen = true
        }
    }
}
