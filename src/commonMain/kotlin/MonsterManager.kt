import animate.*
import com.soywiz.klock.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import com.soywiz.korma.geom.*
import behavior.*
import screens.*

class MonsterManager(private val player: PlayerCharacter, private val pink: Pink) {

    val monsters = mutableListOf<Monster>()

    fun spawnMonster(numOfMonsters: Int) {
        for (countMonsters in 1..numOfMonsters) {
            val monster = Monster(
                pink.idleAnimation,
                pink.deathAnimation,
                pink.walkAnimation,
                1,
                40.0,
                pink.hurtAnimation,
                pink.attackAnimation,
            )
            monsters.add(monster)
        }
    }

    fun update() {
        monsters.forEach { monster ->
            monster.addUpdater {
                monster.playAnimation(monster.idleAnimation, spriteDisplayTime = 100.milliseconds)
                if (monster.isDead) {
                    monsters.remove(monster)
                    monster.removeFromParent()
                }
                if (monster.distanceTo(player) <= 80) {
                    monster.walkTo(player.x, player.y)
                    if (monster.distanceTo(player) <= 50) {
                        monster.attack(player)
                        player.takeDamage(0.01)
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
        }
    }

    fun clearMonsters() {
        monsters.forEach { monster ->
            monsters.remove(monster)
            monster.removeFromParent()
        }
    }
}
