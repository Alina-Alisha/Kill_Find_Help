import animate.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import logicOfBehavior.*
import screens.*

class LivesManager(private val scene: SceneContainer, private val player: PlayerCharacter, private val heart: Health) {

    private val lives = mutableListOf<HealthAnimate>()

    var health = player.health.toInt()

    fun spawnLives() {
        if (lives.size != health) {
            delLives()
            for (countMonsters in 1..health) {
                val live = HealthAnimate(heart.idleAnimation, heart.dyingAnimation, heart.deadAnimation)
                lives.add(live)
            }
        }
    }

    private fun drawLives() {
        val h = player.health.toInt()
        val offset = 35 //длина спрайта сердечка
        val x0 = scene.width / 2
        var x = if (h % 2 == 0)
            x0 - offset * h / 2
        else
            x0 - offset * (h + 1) / 2
        val y = 10.0
        lives.forEach { live ->
            live.xy(x, y)
            live.scale(1.2)
            drawS(live)
            scene.addChild(live)
            x+=offset
        }
    }

    private fun delLives() {
        lives.forEach { live ->
            lives.remove(live)
            live.removeFromParent()
        }
    }

    fun update() {
        //spawnLives(player.health.toInt())
        //println(player.health.toInt())
        lives.forEach { live ->
            drawLives()
            live.addUpdater {
                //spawnLives()
                if (player.health.toInt() == health - 1) {
                    lives[lives.size - 1].alive = false
                    health --
                }
                if (!live.alive) {
                    dying(it)
                    lives.remove(live)
                    //live.removeFromParent()
                }
                //println(player.health.toInt())
            }
        }
    }
}
