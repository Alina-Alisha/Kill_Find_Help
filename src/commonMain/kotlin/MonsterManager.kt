import animate.*
import logicOfBehavior.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import com.soywiz.korio.async.*
import com.soywiz.korma.geom.*
import screens.*

class MonsterManager(private val scene: SceneContainer, private val player: PlayerCharacter, private val pink: Pink) {

    private val monsters = mutableListOf<NewMonster>()

    private fun spawnMonster(monster: NewMonster) {
        monsters.add(monster)
        scene.addChild(monster)
        monster.addUpdater {
            if (monster.health <= 0) {
                monsters.remove(monster)
                monster.removeFromParent()
            }
            if (monster.distanceTo(player) <= 90) {
                scene.launch {
                    monster.walkTo(player.x)
                }
            }
        }
    }

    fun creatingMonsters(numOfMonsters: Int) {
        for (countMonsters in 1..numOfMonsters) {
            val monster = NewMonster(
                pink.idleAnimation,
                pink.hurtAnimation,
                pink.deathAnimation,
                pink.walkAnimation,
                pink.walkAnimation,
                pink.attackAnimation,
                1,
                10
            )
            spawnMonster(monster)
        }
    }

    fun update() {
        monsters.forEach { monster ->

            monster.onCollision({ it == player && player.isAttacking }) {
                monster.takeDamage(player.power)
                monster.attack(player)
            }

            // Проверка столкновений с игроком или другими объектами
            if (monster.distanceTo(player) <= 50) {
                monster.attack(player)
            }

            if (monster.isDead)
                monsters.remove(monster)
        }
    }

    fun drawMonsters() {
        monsters.forEach { monster ->
            monster.xy(rand(20, 500), rand(70, 250))
            monster.scale(2)
            drawS(monster)
            scene.addChild(monster)
        }
    }
}
