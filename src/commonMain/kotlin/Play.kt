import com.soywiz.klock.*
import com.soywiz.korev.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import com.soywiz.korge.view.tween.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*
import kotlin.random.*

fun rand(start: Int, end: Int): Int {
    require(!(start > end || end - start + 1 > Int.MAX_VALUE)) { "Illegal Argument" }
    return Random(System.nanoTime()).nextInt(end - start + 1) + start
}
class Play() : Scene() {
    override suspend fun SContainer.sceneInit() {
        //readImage("game_background_4.jpg")).dockedTo(Anchor.TOP_LEFT, ScaleMode.EXACT)
        val x0 = sceneContainer.width/2
        val y0 = sceneContainer.height/2
        var img = image ( resourcesVfs["m_game_background_4.png"].readBitmap(), 0.5, 0.5) {
            x = x0
            y = y0
        }
        val spriteMapIdleDoll = resourcesVfs["doll/Idle.png"].readBitmap()
        val spriteMapWalkRight = resourcesVfs["doll/Walk_R.png"].readBitmap()
        val spriteMapWalkLeft = resourcesVfs["doll/Walk_L.png"].readBitmap()
        val spriteMapJump = resourcesVfs["doll/Jump.png"].readBitmap()
        val spriteMapAttack = resourcesVfs["doll/Attack_2.png"].readBitmap()

        val spriteMapIdleMonster = resourcesVfs["pink/Pink_Monster_Idle_4.png"].readBitmap()
        val spriteMapHurt = resourcesVfs["pink/Pink_Monster_Hurt_4.png"].readBitmap()
        val spriteMapDeath = resourcesVfs["pink/Pink_Monster_Death_8.png"].readBitmap()
        val spriteMapWalkRightMonster = resourcesVfs["pink/Pink_Monster_Walk_Right.png"].readBitmap()

        val doll = Doll(spriteMapIdleDoll, spriteMapWalkRight, spriteMapWalkLeft, spriteMapJump, spriteMapAttack)
        val pink = Pink(spriteMapIdleMonster, spriteMapHurt, spriteMapDeath, spriteMapWalkRightMonster)

        val player = PlayerCharacter(
            doll.idleAnimation,
            doll.walkLeftAnimation,
            doll.walkRightAnimation,
            doll.attackAnimation
        ).apply {
            xy(200, 200)
        }

//        val monsters = Array(1) {
//            Monster(pink.idleAnimation).apply {
//                scale(2)
//                xy(450, 350)
//            }
//        }

        addChild(player)
        player.addUpdater { time ->
            val scale = 16.milliseconds / time
            val disp = 2 * scale
            val keys = views.input.keys

            val input = views.input
            if (input[MouseButton.LEFT]) {
                player.playAnimation(
                    spriteAnimation = doll.attackAnimation,
                    times = 1,
                    spriteDisplayTime = 100.milliseconds
                )
                player.isAttacking = true
            }

            player.handleKeys(keys, disp, doll.idleAnimation, doll.attackAnimation)
            //if (keys[Key.SPACE]) { player.playAnimation(spriteAnimation = doll.jumpAnimation, times = 0, spriteDisplayTime = 100.milliseconds) }
        }

        var numOfMonsters = 0
        var allMonsters = 0
        while (allMonsters <= 2) {
            var x = rand(20, 500)
            var y = rand(70, 250)
            val monster = Monster(pink.idleAnimation).apply {
                scale(2)
                xy(x, y)
            }
            addChild(monster)
            allMonsters++
            println(allMonsters)
            monster.addUpdater {
                monster.animate(pink.idleAnimation, pink.walkAnimation, it)
            }
            monster.onCollision({ it == player && player.isAttacking }) {
                monster.hurt(
                    pink.hurtAnimation,
                    pink.deathAnimation
                )
                player.isAttacking = false
                monster.isHurt = false
                //if (monster.alive == 0)
                //    removeChildAt(getChildIndex(monster))
            }

        }
    }
}

