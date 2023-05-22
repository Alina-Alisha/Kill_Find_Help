package logicOfBehavior

import com.soywiz.klock.*
import com.soywiz.korev.*
import com.soywiz.korge.input.*
import com.soywiz.korge.view.*

data class KeyAssignment(
    val key: Key,
    var direction: DirectionX,
    val block: (Double) -> Unit
)

data class MouseAssignment(
    val key: MouseButton,
    val animation: SpriteAnimation,
    var isAttacking: Boolean,
    var isOpeningChest: Boolean
)

class PlayerCharacter(
    override val idleAnimation: SpriteAnimation,
    override val deathAnimation: SpriteAnimation,
    override val walkAnimation: SpriteAnimation,
    override val damage: Int,
    override var health: Double,
    openChestAnimation: SpriteAnimation,
    attackAnimation: SpriteAnimation
) : Character(idleAnimation, deathAnimation, walkAnimation, damage, health) {
    override var isAttacking = false
    var isOpeningChest = false
    var power = 1.0
    override var directionX = DirectionX.LEFT

    private val keyAssignments = listOf(
        KeyAssignment(Key.A, DirectionX.LEFT) { x -= it },
        KeyAssignment(Key.D, DirectionX.RIGHT) { x += it },
        KeyAssignment(Key.W, DirectionX.NONE) { y -= it },
        KeyAssignment(Key.S, DirectionX.NONE) { y += it }
    )

    private val mouseAssignments = listOf(
        MouseAssignment(MouseButton.LEFT, attackAnimation, true, false),
        MouseAssignment(MouseButton.RIGHT, openChestAnimation, false, true)
    )

    private var isMoving = false

    fun handleKeys(inputKeys: InputKeys, disp: Double) {
        val anyMovement: Boolean = keyAssignments
            .filter { inputKeys[it.key] }
            .onEach {
                if (it.direction != directionX && it.direction != DirectionX.NONE) {
                    flipSprite(this)
                    directionX = it.direction
                }
                it.block(disp)
                playAnimation(walkAnimation, spriteDisplayTime = 100.milliseconds)
                isAttacking = false
                isOpeningChest = false
            }
            .any()

        if (anyMovement != isMoving) {
            if (isMoving) playAnimationLooped(idleAnimation, spriteDisplayTime = 100.milliseconds)
            isMoving = anyMovement
        }
    }

    fun mouseEvents(input: Input) {
        var anyMovement: Boolean = mouseAssignments
            .filter { input[it.key] }
            .onEach {
                playAnimationForDuration(0.7.seconds, it.animation, spriteDisplayTime = 100.milliseconds)
                isAttacking = it.isAttacking
                isOpeningChest = it.isOpeningChest
            }
            .any()
    }

}
