package logicOfBehavior

import com.soywiz.klock.*
import com.soywiz.korge.view.*

class LootAnimate(
    idleAnimation: SpriteAnimation
) : Sprite(idleAnimation) {
    private var lives = 10.0
    var alive = false
    fun animate(idleAnimation: SpriteAnimation, delta: TimeSpan) {
        if (alive) {
            this.y -= 1.5
            lives -= delta.seconds
            playAnimation(spriteAnimation = idleAnimation, spriteDisplayTime = 600.milliseconds)
        }
        if (lives <= 0)
            parent?.removeChild(this)
    }
}
