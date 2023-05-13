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
    walkLeftAnimation: SpriteAnimation, walkRightAnimation: SpriteAnimation
) : Sprite(idleAnimation) {
    var health = 3
    var isAttacking = false
    var isOpeningChest = false

    //var notAttacking = true
    private val assignments = listOf(
        KeyAssignment(Key.A, walkLeftAnimation) { x -= it },
        KeyAssignment(Key.D, walkRightAnimation) { x += it },
        KeyAssignment(Key.W, walkRightAnimation) { y -= it },
        KeyAssignment(Key.S, walkRightAnimation) { y += it },
    )

    private var isMoving = false


    fun handleKeys(inputKeys: InputKeys, disp: Double, idleAnimation: SpriteAnimation) {
        val anyMovement: Boolean = assignments // Iterate all registered movement keys
            .filter { inputKeys[it.key] } // Check if this movement key was pressed
            .onEach {
                // If yes, perform its corresponding action and play the corresponding animation
                it.block(disp)
                playAnimation(it.animation, spriteDisplayTime = 100.milliseconds)
                isAttacking = false
                isOpeningChest = false
            }
            .any()

        if (anyMovement != isMoving) {
            if (isMoving) playAnimationLooped(idleAnimation)
            isMoving = anyMovement
        }
    }
}
