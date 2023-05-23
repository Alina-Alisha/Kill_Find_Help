package logicOfBehavior

import MonsterManager
import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import com.soywiz.korim.font.*

class Titles(private val scene: SceneContainer, val player: PlayerCharacter, val monsterManager: MonsterManager, val font: TtfFont) {
    fun update() {
        if (monsterManager.monsters.size == 0) {
            MyModule.level += 1
            var winText = scene.text("YOU WIN", 70.0, Colors.WHITE, font) {
                position(scene.views.virtualWidth / 2 - 100, scene.views.virtualHeight / 2 - 60)
            }
        } else if(player.isDead) {
            var looseText = scene.text("YOU ARE DEAD", 70.0, Colors.WHITE, font) {
                position(scene.views.virtualWidth / 2 - 200, scene.views.virtualHeight / 2 - 60)
            }
        }
    }
}
