import com.soywiz.klock.*
import com.soywiz.korev.*
import com.soywiz.korge.input.*
import com.soywiz.korge.view.*

data class KeyAssignment(
    val key: Key,
    val animation: SpriteAnimation,
    val block: (Double) -> Unit
)

class PlayerCharacter(
    idleAnimation: SpriteAnimation,
    walkLeftAnimation: SpriteAnimation, walkRightAnimation: SpriteAnimation,
    attackAnimation: SpriteAnimation
) : Sprite(idleAnimation) {
    var isAttacking = false
    //var notAttacking = true
    private val assignments = listOf(
        KeyAssignment(Key.LEFT, walkLeftAnimation) { x -= it },
        KeyAssignment(Key.RIGHT, walkRightAnimation) { x += it },
        KeyAssignment(Key.UP, walkRightAnimation) { y -= it },
        KeyAssignment(Key.DOWN, walkRightAnimation) { y += it },
    )

    private var isMoving = false


    fun handleKeys(inputKeys: InputKeys, disp: Double, idleAnimation: SpriteAnimation, attackAnimation: SpriteAnimation) {
        val anyMovement: Boolean = assignments // Iterate all registered movement keys
            .filter { inputKeys[it.key] } // Check if this movement key was pressed
            .onEach {
                // If yes, perform its corresponding action and play the corresponding animation
                it.block(disp)
                playAnimation(it.animation, spriteDisplayTime = 100.milliseconds)
                isAttacking = false
            }
            .any()
        //if (!isMoving) playAnimation(idleAnimation, spriteDisplayTime = 100.milliseconds)

        if (anyMovement != isMoving) {
            if (isMoving) playAnimationLooped(idleAnimation)
            isMoving = anyMovement
        }
        /*if (isAttacking)
        {
            playAnimation(spriteAnimation = attackAnimation, spriteDisplayTime = 100.milliseconds)
            isAttacking = false
        }*/
        //if (inputKeys[Key.SPACE] ) { playAnimation(spriteAnimation = jumpAnimation, times = 0, spriteDisplayTime = 100.milliseconds)}
    }
}
