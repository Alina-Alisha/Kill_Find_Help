import com.soywiz.klock.*
import com.soywiz.korev.*
import com.soywiz.korge.input.*
import com.soywiz.korge.view.*
enum class Direction {
    RIGHT, LEFT
}
data class KeyAssignment(
    val key: Key,
    val animation: SpriteAnimation,
    var direction: Direction,
    val block: (Double) -> Unit
)

data class MouseAssignment(
    val key: MouseButton,
    val animation: SpriteAnimation,
    var direction: Direction
    //val block: (Double) -> Unit
)

class PlayerCharacter(
    val idleAnimation: SpriteAnimation,
    val walkLeftAnimation: SpriteAnimation,
    val walkRightAnimation: SpriteAnimation,
    val openChestRightAnimation: SpriteAnimation,
    val openChestLeftAnimation: SpriteAnimation,
    val attackRightAnimation: SpriteAnimation,
    val attackLeftAnimation: SpriteAnimation
) : Sprite(idleAnimation) {
    var health = 30
    var isAttacking = false
    var isOpeningChest = false
    var power = 1

//    val idle = playAnimation(idleAnimation)
//    val openChestRight = playAnimation(openChestRightAnimation)
//    val openChestLeft = playAnimation(openChestLeftAnimation)
//    val walkRight = playAnimation(walkRightAnimation)
//    val walkLeft = playAnimation(walkLeftAnimation)
//    val attackRight = playAnimation(attackRightAnimation)
//    val attackLeft = playAnimation(attackLeftAnimation)

    private var lastAnimation = walkLeftAnimation
    private var direction = Direction.LEFT

    private val keyAssignments = listOf(
        KeyAssignment(Key.A, walkLeftAnimation, Direction.LEFT) { x -= it },
        KeyAssignment(Key.D, walkRightAnimation, Direction.RIGHT) { x += it },
        KeyAssignment(Key.W, lastAnimation, direction) { y -= it },
        KeyAssignment(Key.S, lastAnimation, direction) { y += it },
    )

    private val mouseAssignments = listOf(
        MouseAssignment(MouseButton.LEFT, attackRightAnimation, direction),
        MouseAssignment(MouseButton.RIGHT, openChestRightAnimation, direction)
    )

    private var isMoving = false

    fun handleKeys(inputKeys: InputKeys, disp: Double) {
        val anyMovement: Boolean = keyAssignments
            .filter { inputKeys[it.key] }
            .onEach {
                it.block(disp)
                playAnimation(it.animation, spriteDisplayTime = 100.milliseconds)
                lastAnimation = it.animation
                direction = it.direction
                isAttacking = false
                isOpeningChest = false
            }
            .any()

        if (anyMovement != isMoving) {
            if (isMoving) playAnimationLooped(idleAnimation)
            isMoving = anyMovement
        }
    }

    fun mouseEvents(input: Input) {
        val anyMovement: Boolean = mouseAssignments
            .filter { input[it.key] }
            .onEach {
                playAnimation(it.animation, spriteDisplayTime = 100.milliseconds)
                lastAnimation = it.animation
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
