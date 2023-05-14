import com.soywiz.klock.*
import com.soywiz.korev.*
import com.soywiz.korge.input.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.ui.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import com.soywiz.korim.font.*
import com.soywiz.korim.format.*
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

        val spriteMapChest = resourcesVfs["AoM2.png"].readBitmap()

        val spriteMapRedPotion = resourcesVfs["Red Potion.png"].readBitmap()

        val spriteMapIdleDoll = resourcesVfs["doll/Idle.png"].readBitmap()
        val spriteMapWalkRight = resourcesVfs["doll/Walk_R.png"].readBitmap()
        val spriteMapWalkLeft = resourcesVfs["doll/Walk_L.png"].readBitmap()
        val spriteMapOpenChest = resourcesVfs["doll/chest_opening.png"].readBitmap()
        val spriteMapAttack = resourcesVfs["doll/Attack_2.png"].readBitmap()

        val spriteMapIdleMonster = resourcesVfs["pink/Pink_Monster_Idle_4.png"].readBitmap()
        val spriteMapHurt = resourcesVfs["pink/Pink_Monster_Hurt_4.png"].readBitmap()
        val spriteMapDeath = resourcesVfs["pink/Pink_Monster_Death_8.png"].readBitmap()
        val spriteMapWalkRightMonster = resourcesVfs["pink/Pink_Monster_Walk_Right.png"].readBitmap()

        val spriteMapHeart = resourcesVfs["health.png"].readBitmap()

        val heart = Health(spriteMapHeart)

        val doll = Doll(spriteMapIdleDoll, spriteMapWalkRight, spriteMapWalkLeft, spriteMapOpenChest, spriteMapAttack)
        val pink = Pink(spriteMapIdleMonster, spriteMapHurt, spriteMapDeath, spriteMapWalkRightMonster)

        val player = PlayerCharacter(
            doll.idleAnimation,
            doll.walkLeftAnimation,
            doll.walkRightAnimation
        ).apply {
            xy(200, 200)
        }
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
            if (input[MouseButton.RIGHT]) {
                player.playAnimation(
                    spriteAnimation = doll.openChestAnimation,
                    times = 1,
                    spriteDisplayTime = 100.milliseconds
                )
                player.isOpeningChest = true
            }
            player.handleKeys(keys, disp, doll.idleAnimation)
            val offset = 35 //длина спрайта сердечка
            var x = if (player.health % 2 == 0)
                x0 - offset * player.health / 2
            else
                x0 - offset * (player.health + 1) / 2
            val y = 10.0
            for (allLives in 1..player.health) {
                val live = HealthAnimate(heart.idleAnimation).apply {
                    xy(x, y)
                }
                addChild(live)
                x += offset
            }
        }

        val loot = Loot(spriteMapRedPotion)
        val blueChest = Chest(spriteMapChest)
        val chest =
            ChestAnimate(blueChest.idleAnimation, 1).apply { xy(rand(20, 450), rand(70, 250)) }
        addChild(chest)
        val redPotion = LootAnimate(loot.idleAnimation)
        var a = chest.numOfLoot
        chest.onCollision({ it == player && player.isOpeningChest }) {
            chest.open(blueChest.openAnimation)
            if (player.isOpeningChest) {
                redPotion.apply {
                    scale(1.5)
                    xy(chest.x + 20, chest.y + 30)
                }
                addChild(redPotion)
                redPotion.alive = true
                redPotion.addUpdater {
                    redPotion.animate(loot.idleAnimation, it)
                }
                if (a > 0) {
                    player.health += chest.numOfLoot
                    println(player.health)
                    a--
                }
            }
        }

        var numOfMonsters = 0
        val n = MyModule.level
        when (MyModule.level) {
            1 -> numOfMonsters = 2
            2 -> numOfMonsters = 4
            3 -> numOfMonsters = 7
        }
        val monsters = mutableListOf<Monster>()

        for (allMonsters in 1..numOfMonsters) {
            val x = rand(20, 500)
            val y = rand(70, 250)
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
//                    launch {
//                        sceneContainer.changeTo<GameMenu>()
//                    }
                    var winText = text("YOU WIN", 70.0, Colors.BLACK, font) {
                        position(views.virtualWidth / 2 - 100, views.virtualHeight / 2 - 60)
                    }
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

        var menuButton = uiButton(100.0, 32.0) {
            text = "menu"
            textFont = font
            position(0, 0)
            onClick {
                sceneContainer.changeTo<GameMenu>()
            }
        }
    }
}

