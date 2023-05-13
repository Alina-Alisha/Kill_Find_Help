import com.soywiz.klock.*
import com.soywiz.korev.*
import com.soywiz.korge.input.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.ui.*
import com.soywiz.korge.view.*
import com.soywiz.korge.view.onClick
import com.soywiz.korge.view.tween.*
import com.soywiz.korim.color.*
import com.soywiz.korim.font.*
import com.soywiz.korim.format.*
import com.soywiz.korio.async.*
import com.soywiz.korio.file.std.*
import kotlin.random.*

fun rand(start: Int, end: Int): Int {
    require(!(start > end || end - start + 1 > Int.MAX_VALUE)) { "Illegal Argument" }
    return Random(System.nanoTime()).nextInt(end - start + 1) + start
}

class Play() : Scene() {
    var change = false
    override suspend fun SContainer.sceneInit() {
        val font = resourcesVfs["mr_countryhouseg_0.ttf"].readTtfFont(preload = false)
        val x0 = sceneContainer.width / 2
        val y0 = sceneContainer.height / 2
        var img = image(resourcesVfs["m_game_background_4.png"].readBitmap(), 0.5, 0.5) {
            x = x0
            y = y0
        }

        val spriteMapChest = resourcesVfs["chest.png"].readBitmap()

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

        val chest1 = Chest(spriteMapChest)
        var x = rand(20, 500)
        var y = rand(70, 250)
        val chest = ChestAnimate(chest1.idleAnimation).apply { xy(x, y) }
        //playAnimation(spriteAnimation = chest.idleAnimation)
        addChild(chest)
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
        }
        var numOfMonsters = 0
        val n = MyModule.level
        when (MyModule.level) {
            1 -> numOfMonsters = 2
            2 -> numOfMonsters = 4
            3 -> numOfMonsters = 7
        }
        var monsters = mutableListOf<Monster>()

        if (MyModule.level == n) {
            var winTime = 1000000.0
            for (allMonsters in 1..numOfMonsters) {
                var x = rand(20, 500)
                var y = rand(70, 250)
                val monster = Monster(pink.idleAnimation).apply {
                    scale(2)
                    xy(x, y)
                }
                addChild(monster)
                monsters.add(monster)
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
                    if (monster.alive == 0) {
                        monsters.remove(monster)
                    }
                    if (monsters.size == 0) {
                        change = true
                        MyModule.level = n + 1
                        println(MyModule.level)
                        launch {
                                sceneContainer.changeTo<GameMenu>()
                        }
//                        var winText = text("YOU WIN", 30.0, Colors.BLACK, font) {
//                            position(views.virtualWidth / 2 - 70, views.virtualHeight / 2 - 140)
//                        }
//                        while (change && winTime >= 0) {
//                            winTime -= 0.001
//                        }
//                        if (winTime < 0) {
//                            launch {
//                                sceneContainer.changeTo<GameMenu>()
//                            }
//                        }
                    }
                }

            }

        }


        var menuButton = uiTextButton(100.0, 32.0) {
            text = "menu"
            position(0, 0)
            onClick {
                sceneContainer.changeTo<GameMenu>()
            }
        }
        //addUpdater { if (MyModule.level == n + 1) sceneContainer.changeTo<GameMenu>() }
    }
}

