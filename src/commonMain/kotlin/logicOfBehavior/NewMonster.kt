package logicOfBehavior

import PlayerCharacter
import com.soywiz.klock.*
import com.soywiz.korge.time.*
import com.soywiz.korge.view.*
import kotlin.random.*


fun rand(start: Int, end: Int): Int {
    require(!(start > end || end - start + 1 > Int.MAX_VALUE)) { "Illegal Argument" }
    return Random(System.nanoTime()).nextInt(end - start + 1) + start
}

class NewMonster(
    idleAnimation: SpriteAnimation,
    hurtAnimation: SpriteAnimation,
    deathAnimation: SpriteAnimation,
    walkRightAnimation: SpriteAnimation,
    walkLeftAnimation: SpriteAnimation,
    attackAnimation: SpriteAnimation,
    private val damage: Int,
    //private val speed: Double,
    var health: Int
) : Sprite() {

    private var deadCooldown = 0.16
    val idle = playAnimation(idleAnimation)
    val hurt = playAnimation(hurtAnimation)
    val death = playAnimation(deathAnimation)
    val walkRight = playAnimation(walkRightAnimation)
    val walkLeft = playAnimation(walkLeftAnimation)
    val attackAnim = playAnimation(attackAnimation)

    var timeAttack = DateTime.now()
    var lastTimeAttack = DateTime.now()

    var isDead = false

    var direction = Direction.RIGHT

    enum class Direction {
        RIGHT, LEFT
    }

    fun attack(target: PlayerCharacter) {
        timeAttack = DateTime.now()
        if (timeAttack - lastTimeAttack > TimeSpan(1000.0)) {
            attackAnim
            target.health - damage
            lastTimeAttack = timeAttack
        }
    }

    fun takeDamage(damage: Int) {
        health -= damage
        if (health <= 0) {
            die(TimeSpan(0.5))
        } else {
            hurt
        }
    }

    private fun die(delta: TimeSpan) {
        if (deadCooldown > 0) {
            death
            deadCooldown -= delta.seconds
            isDead = true
        } else removeFromParent()
    }

    suspend fun walkTo(targetX: Double) {
        val deltaX = targetX - x
        direction = if (deltaX > 0) {
            walkRight
            Direction.RIGHT
        } else {
            walkLeft
            Direction.LEFT
        }
        while (x != targetX) {
            x += speed * TimeSpan(0.16).milliseconds * if (direction == Direction.RIGHT) 1 else -1
            delayFrame()
        }
        idle
    }

//    fun draw() {
//        val x = rand(20, 500)
//        val y = rand(70, 250)
//        this.
//    }

//    fun update() {
//
//    }

}
