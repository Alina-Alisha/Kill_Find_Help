package behavior

import com.soywiz.klock.*
import com.soywiz.korge.view.*

enum class DirectionX {
    RIGHT, LEFT, NONE
}

enum class DirectionY {
    UP, DOWN
}

open class Character(
    open val idleAnimation: SpriteAnimation,
    open val deathAnimation: SpriteAnimation,
    open val walkAnimation: SpriteAnimation,
    open val damage: Int,
    open var health: Double
) : Sprite(idleAnimation) {
    open var isAttacking = false
    open var directionX = DirectionX.LEFT
    open var deadCooldown = 0.16
    open var isDead = false

    fun flipSprite(sprite: Character) {
        if (sprite.directionX == DirectionX.LEFT) {
            sprite.scaleX = 1.0
            sprite.x -= 56
        } else if (sprite.directionX == DirectionX.RIGHT) {
            sprite.x += 56
            sprite.scaleX = -1.0
        }
    }

    open fun takeDamage(damage: Double) {
        health -= damage
        if (health <= 0) {
            die(TimeSpan(2.3))
        }
    }

    fun die(delta: TimeSpan) {
        if (deadCooldown > 0) {
            playAnimation(deathAnimation, spriteDisplayTime = 300.milliseconds)
            deadCooldown -= delta.seconds
            isDead = true
        } else {
            this.removeFromParent()
        }
    }
}
