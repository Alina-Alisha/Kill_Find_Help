import com.soywiz.klock.*
import com.soywiz.korev.*
import com.soywiz.korge.*
import com.soywiz.korge.input.*
import com.soywiz.korge.time.*
import com.soywiz.korge.view.*
import com.soywiz.korma.geom.*

data class Animations(
    val animation: SpriteAnimation,
    val block: (Double) -> Unit
)

class Monster(
    idleAnimation: SpriteAnimation
) : Sprite(idleAnimation) {

    var alive = 10
    var isHurt = false
    private val self_x = x
    private val self_y = y
    var deadCooldown = 0.1
    fun animate(idleAnimation: SpriteAnimation, walkAnimation: SpriteAnimation, delta: TimeSpan) {
        if (alive == 0)
            deadCooldown -= delta.seconds
        if (deadCooldown < 0)
            parent?.removeChild(this)
        if (alive > 0 && !isHurt) {
            playAnimation(spriteAnimation = idleAnimation, spriteDisplayTime = 200.milliseconds)
            /*if (x <= self_x + 50 || x >= self_x - 50) {
                playAnimation(spriteAnimation = walkAnimation, times = 1, spriteDisplayTime = 200.milliseconds)
                x++
            } else {
                stopAnimation() //тоже не робит
                //playAnimation(spriteAnimation = idleAnimation, spriteDisplayTime = 200.milliseconds)
            }*/
        }

    }

    fun hurt(hurtAnimation: SpriteAnimation, deathAnimation: SpriteAnimation) {
        if (alive > 0) {
            isHurt = true
            playAnimation(spriteAnimation = hurtAnimation, spriteDisplayTime = 200.milliseconds)
            alive -= 1
        }
        else playAnimation(spriteAnimation = deathAnimation, spriteDisplayTime = 200.milliseconds)
    }
}
