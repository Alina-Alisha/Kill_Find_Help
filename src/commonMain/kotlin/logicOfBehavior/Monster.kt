package logicOfBehavior

import PlayerCharacter
import com.soywiz.klock.*
import com.soywiz.korge.view.*
import com.soywiz.korma.geom.*

class Monster(
    idleAnimation: SpriteAnimation
) : Sprite(idleAnimation) {

    var alive = 10
    var isHurt = false
    private var deadCooldown = 0.16
    private var back = true
    var isAttacking = false
    var timeAttack = DateTime.now()
    var lastTimeAttack = DateTime.now()
    fun animate(
        idleAnimation: SpriteAnimation,
        walkAnimation: SpriteAnimation,
        attackAnimation: SpriteAnimation,
        delta: TimeSpan,
        player: PlayerCharacter
    ) {
        if (alive == 0)
            deadCooldown -= delta.seconds
        if (deadCooldown < 0)
            removeFromParent()
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
            //println(IPoint(x, y).distanceTo(IPoint(playerPosition.first, playerPosition.second)))
            if (this.distanceTo(player) < 70.5) {
                val dirX = player.x - x + 64
                val dirY = player.y - y + 64
                x += dirX / 100
                y += dirY / 100
                playAnimation(spriteAnimation = walkAnimation, spriteDisplayTime = 200.milliseconds)
            }
            if (this.distanceTo(player) < 45.0) {
                timeAttack = DateTime.now()
                println(timeAttack - lastTimeAttack)
                if (timeAttack - lastTimeAttack >= TimeSpan(10000.0)) {
                    playAnimation(spriteAnimation = attackAnimation, spriteDisplayTime = 200.milliseconds)
                    isAttacking = true
                    lastTimeAttack = timeAttack
                }
                else isAttacking = false
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
