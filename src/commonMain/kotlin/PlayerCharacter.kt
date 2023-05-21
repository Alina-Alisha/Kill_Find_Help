import com.soywiz.klock.*
import com.soywiz.korev.*
import com.soywiz.korge.input.*
import com.soywiz.korge.view.*
enum class Direction {
    RIGHT, LEFT
}
data class KeyAssignment(
    val key: Key,
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

    val idle = playAnimation(idleAnimation)
    val openChestRight = playAnimation(openChestRightAnimation)
    val openChestLeft = playAnimation(openChestLeftAnimation)
    val walk = playAnimation(walkRightAnimation)
    val attackRight = playAnimation(attackRightAnimation)
    val attackLeft = playAnimation(attackLeftAnimation)

    private var lastAnimation = walkLeftAnimation
    private var direction = Direction.LEFT

    private val keyAssignments = listOf(
        KeyAssignment(Key.A, Direction.LEFT) { x -= it },
        KeyAssignment(Key.D, Direction.RIGHT) { x += it },
        KeyAssignment(Key.W, direction) { y -= it },
        KeyAssignment(Key.S, direction) { y += it },
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
                if (direction == Direction.LEFT) { //поправить телепортацию из-за отражения
                    this.scaleX = -1.0
                }
                else {
                    this.scaleX = 1.0
                }
                it.block(disp)
                walk
                direction = it.direction
                isAttacking = false
                isOpeningChest = false
            }
            .any()

        if (isAttacking)
            attackLeft
        if (isOpeningChest)
            openChestLeft

        if (anyMovement != isMoving) {
            if (isMoving) playAnimationLooped(idleAnimation)
            isMoving = anyMovement
        }
    }

    fun mouseEvents(input: Input, disp: Double) {
        var anyMovement: Boolean = mouseAssignments
            .filter { input[it.key] }
            .onEach {
                if (direction == Direction.LEFT)
                    this.scaleX = -1.0
                else
                    this.scaleX = 1.0
                playAnimationForDuration(0.7.seconds, it.animation, spriteDisplayTime = 100.milliseconds)
                lastAnimation = it.animation
                isAttacking = false
                isOpeningChest = false
                setFrame(100)
            }
            .any()

//        if (anyMovement != isMoving) {
//            if (isMoving) playAnimationLooped(idleAnimation, spriteDisplayTime = 200.milliseconds)
//            isMoving = anyMovement
//        }
    }

}
