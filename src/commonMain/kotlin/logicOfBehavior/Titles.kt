package logicOfBehavior

import MonsterManager
import com.soywiz.klock.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import com.soywiz.korim.font.*
import com.soywiz.korio.async.*
import screens.*

class Titles(private val scene: SceneContainer, val player: PlayerCharacter, val monsterManager: MonsterManager, val font: TtfFont)  {
    private var checkout = 3.0
    var winOrLoose = false
    fun update(delta: TimeSpan) {
        if (monsterManager.monsters.size == 0 && !player.isDead) {
            MyModule.level += 1
            var winText = scene.text("YOU WIN", 70.0, Colors.WHITE, font) {
                position(scene.views.virtualWidth / 2 - 100, scene.views.virtualHeight / 2 - 60)
            }
            winOrLoose = true
        } else if(player.isDead && monsterManager.monsters.size > 0) {
            winOrLoose = true
            var looseText = scene.text("YOU ARE DEAD", 70.0, Colors.WHITE, font) {
                position(scene.views.virtualWidth / 2 - 200, scene.views.virtualHeight / 2 - 60)
            }
            if (checkout > 0) {
                checkout -= delta.seconds
            } else {
                monsterManager.clearMonsters()
                looseText.removeFromParent()
                scene.launch { scene.changeTo<GameMenu>() }
            }
        }
    }
}
