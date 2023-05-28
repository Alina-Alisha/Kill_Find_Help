package behavior

import com.soywiz.klock.*
import com.soywiz.korge.view.*

class Monster(
    override val idleAnimation: SpriteAnimation,
    override val deathAnimation: SpriteAnimation,
    override val walkAnimation: SpriteAnimation,
    override val damage: Int,
    override var health: Double,
    private val hurtAnimation: SpriteAnimation,
    private val attackAnimation: SpriteAnimation
) : Character(idleAnimation, deathAnimation, walkAnimation, damage, health) {

    override var deadCooldown = 0.16

    private var timeAttack = DateTime.now()
    var lastTimeAttack = DateTime.now()
    override var isDead = false
    override var directionX = DirectionX.RIGHT
    var directionY = DirectionY.DOWN
    override var isAttacking = false


    fun attack(target: PlayerCharacter) {
        timeAttack = DateTime.now()
        if (timeAttack - lastTimeAttack > TimeSpan(1000.0)) {
            playAnimationForDuration(2.seconds, attackAnimation, spriteDisplayTime = 150.milliseconds)
            target.health - damage
            lastTimeAttack = timeAttack
            isAttacking = true
        } else isAttacking = false
    }

    override fun takeDamage(damage: Double) {
        health -= damage
        if (health <= 0) {
            die(TimeSpan(0.5))
        } else {
            playAnimation(hurtAnimation)
        }
    }


    fun walkTo(targetX: Double, targetY: Double) {
        var dirX = targetX - x
        var dirY = targetY - y
        directionX = if (dirX > 0) DirectionX.RIGHT else DirectionX.LEFT
        playAnimation(spriteAnimation = walkAnimation, spriteDisplayTime = 200.milliseconds)
        directionY = if (dirY > 0) {
            DirectionY.DOWN
        } else {
            DirectionY.UP
        }
        dirX += if (directionX == DirectionX.RIGHT) 28 else -28
        dirY += if (directionY == DirectionY.DOWN) 36 else -36
        x += dirX / 100
        y += dirY / 100
        //flipSprite(this) //раздвоение личности
    }

}
