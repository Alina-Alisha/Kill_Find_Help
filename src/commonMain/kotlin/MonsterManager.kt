import animate.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import com.soywiz.korma.geom.*
import screens.*

class MonsterManager(private val scene: SceneContainer, private val player: PlayerCharacter, private val pink: Pink) {

    private val monsters = mutableListOf<NewMonster>()

    private fun spawnMonster(monster: NewMonster) {
        monsters.add(monster)
        scene.addChild(monster)
    }

    fun creatingMonsters(numOfMonsters: Int) {
        for (countMonsters in 1..numOfMonsters) {
            val monster = NewMonster(
                pink.idleAnimation,
                pink.deathAnimation,
                pink.walkAnimation,
                1,
                40,
                pink.hurtAnimation,
                pink.attackAnimation,
            )
            spawnMonster(monster)
        }
    }

    fun update() {
        monsters.forEach { monster ->
            monster.addUpdater {
                if (monster.isDead) {
                    monsters.remove(monster)
                    monster.removeFromParent()
                }
                if (monster.distanceTo(player) <= 80) {
                    monster.walkTo(player.x, player.y)
                    if (monster.distanceTo(player) <= 50) {
                        monster.attack(player)
                    }
                }
            }
            monster.onCollision({ it == player && player.isAttacking }) {
                monster.takeDamage(player.power)
            }
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
