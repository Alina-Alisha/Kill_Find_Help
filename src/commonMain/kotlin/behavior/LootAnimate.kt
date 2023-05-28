package behavior

import com.soywiz.klock.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import screens.*

class LootAnimate(
    val idleAnimation: SpriteAnimation,
    private val scene: SceneContainer
) : Sprite(idleAnimation) {
    private var lives = 10.0
    var alive = false
    fun animate(delta: TimeSpan) {
        if (alive) {
            this.y -= 1.5
            lives -= delta.seconds
            playAnimation(spriteAnimation = idleAnimation, spriteDisplayTime = 600.milliseconds)
        }
        if (lives <= 0)
            parent?.removeChild(this)
    }

    fun update(chest: ChestAnimate) {
        drawS(this)
        this.xy(chest.x + 20, chest.y + 30)
        this.scale(1.5)
        scene.addChild(this)
        alive = true
    }
}
