package logicOfBehavior

import com.soywiz.klock.*
import com.soywiz.korge.view.*

class HealthAnimate(
    idleAnimation: SpriteAnimation,
    private val dyingAnimation: SpriteAnimation,
    private val deadAnimation: SpriteAnimation
) : Sprite(idleAnimation) {
    var alive = true
    private var lives = 1.5
    fun dying(delta: TimeSpan) {
        if (!alive && lives > 0) {
            playAnimation(spriteAnimation = dyingAnimation, spriteDisplayTime = 500.milliseconds)
            lives -= delta.seconds
        }
        if (lives <= 0) {
            playAnimationLooped(spriteAnimation = deadAnimation)
            this.removeFromParent()
        }
    }
}
