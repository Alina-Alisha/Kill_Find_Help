import com.soywiz.klock.*
import com.soywiz.korge.view.*

class ChestAnimate(
    idleAnimation: SpriteAnimation,
) : Sprite(idleAnimation) {
    var wasOpen = false
    fun open(openAnimation: SpriteAnimation) {
        if (!wasOpen) {
            playAnimation(spriteAnimation = openAnimation, spriteDisplayTime = 350.milliseconds)
            wasOpen = true
        }
    }
}
