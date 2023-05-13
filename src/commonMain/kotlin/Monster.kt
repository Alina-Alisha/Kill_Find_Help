import com.soywiz.klock.*
import com.soywiz.korev.*
import com.soywiz.korge.*
import com.soywiz.korge.input.*
import com.soywiz.korge.time.*
import com.soywiz.korge.view.*
import com.soywiz.korma.geom.*

class Monster(
    idleAnimation: SpriteAnimation
) : Sprite(idleAnimation) {

    var alive = 10
    var isHurt = false
    var deadCooldown = 0.16
    var back = true
    fun animate(idleAnimation: SpriteAnimation, walkAnimation: SpriteAnimation, delta: TimeSpan) {
        if (alive == 0)
        {deadCooldown -= delta.seconds
        println(delta)
        }
        if (deadCooldown < 0)
            parent?.removeChild(this)
        if (alive > 0 && !isHurt) {
            playAnimation(spriteAnimation = idleAnimation, spriteDisplayTime = 200.milliseconds)
            if (x == 50.0) back = false
            if (x == 350.0) back = true
            if (x < 350.0 && !back) {
                x++
                playAnimation(spriteAnimation = walkAnimation, spriteDisplayTime = 200.milliseconds)
            } else if (x > 50.0 && back) {
                playAnimation(spriteAnimation = walkAnimation, spriteDisplayTime = 200.milliseconds)
                x--
            }
        }

    }

    fun hurt(hurtAnimation: SpriteAnimation, deathAnimation: SpriteAnimation) {
        if (alive > 0) {
            isHurt = true
            playAnimation(spriteAnimation = hurtAnimation, spriteDisplayTime = 200.milliseconds)
            alive -= 1
        } else {
            playAnimation(spriteAnimation = deathAnimation, spriteDisplayTime = 200.milliseconds)
        }
    }
}
