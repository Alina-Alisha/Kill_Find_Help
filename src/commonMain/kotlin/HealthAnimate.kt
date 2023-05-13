import com.soywiz.klock.*
import com.soywiz.korge.view.*

class HealthAnimate(
    idleAnimation: SpriteAnimation,
) : Sprite(idleAnimation) {
    var alive = true
    private var lives = 3.0
    fun dying(dyingAnimation: SpriteAnimation, deadAnimation: SpriteAnimation, delta: TimeSpan) {
        if (!alive) {
            playAnimation(spriteAnimation = dyingAnimation, spriteDisplayTime = 500.milliseconds)
            lives -= delta.seconds
        }
        if (lives <= 0)
            playAnimationLooped(spriteAnimation = deadAnimation)
    }
}
