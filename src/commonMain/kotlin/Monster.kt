import com.soywiz.klock.*
import com.soywiz.korev.*
import com.soywiz.korge.*
import com.soywiz.korge.input.*
import com.soywiz.korge.view.*
import com.soywiz.korma.geom.*

data class Animations(
    val animation: SpriteAnimation,
    val block: (Double) -> Unit
)

class Monster(
    idleAnimation: SpriteAnimation,
    walkAnimation: SpriteAnimation
) : Sprite(idleAnimation) {

    var alive = 40
    var isHurt = false
    private val self_x = x
    private val self_y = y
    fun animate(idleAnimation: SpriteAnimation, walkAnimation: SpriteAnimation) {
        if (alive > 1 && !isHurt) {
            //playAnimation(spriteAnimation = idleAnimation, spriteDisplayTime = 200.milliseconds)
            if (x <= self_x + 50 || x >= self_x - 50) {
                playAnimation(spriteAnimation = walkAnimation, times = 1, spriteDisplayTime = 200.milliseconds)
                x++
            } else {
                stopAnimation() //тоже не робит
                //playAnimation(spriteAnimation = idleAnimation, spriteDisplayTime = 200.milliseconds)
            }
        }

    }

    fun hurt(hurtAnimation: SpriteAnimation, deathAnimation: SpriteAnimation) {
        if (alive == 1) {
            playAnimation(spriteAnimation = deathAnimation, spriteDisplayTime = 100.milliseconds) //не выполняется(((
            alive = 0
        } else if (alive > 1) {
            println(alive)
            isHurt = true
            playAnimation(spriteAnimation = hurtAnimation, spriteDisplayTime = 100.milliseconds)
            alive -= 1
        }
    }
}
