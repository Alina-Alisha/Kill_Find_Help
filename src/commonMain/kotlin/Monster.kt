import com.soywiz.klock.*
import com.soywiz.korge.view.*
import kotlin.math.*

class Monster(
    idleAnimation: SpriteAnimation
) : Sprite(idleAnimation) {

    var alive = 10
    var isHurt = false
    private var deadCooldown = 0.16
    private var back = true
    var isAttacking = false
    var timeAttack = 1.0
    fun animate(idleAnimation: SpriteAnimation, walkAnimation: SpriteAnimation, attackAnimation: SpriteAnimation, delta: TimeSpan, playerPosition: Pair<Double, Double>) {
        if (alive == 0)
            deadCooldown -= delta.seconds
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
                playAnimation(spriteAnimation = walkAnimation, spriteDisplayTime = 200.milliseconds, reversed = true)
                x--
            }
            //преследование героя
            if (abs(x-playerPosition.first) < 25.0 || abs(y-playerPosition.second) < 25.0) {
                val dirX = playerPosition.first - x + 64
                val dirY = playerPosition.second - y + 64
                x += dirX/50
                y += dirY/50
                playAnimation(spriteAnimation = walkAnimation, spriteDisplayTime = 200.milliseconds)
            }
            if (abs(x-playerPosition.first) < 5.0 || abs(y-playerPosition.second) < 5.0) {
                playAnimation(spriteAnimation = attackAnimation, spriteDisplayTime = 200.milliseconds)
                isAttacking = true
            } else isAttacking = false
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
