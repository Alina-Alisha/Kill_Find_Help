package screens

import LivesManager
import MonsterManager
import MyModule
import logicOfBehavior.PlayerCharacter
import PlayerManager
import animate.*
import com.soywiz.korge.input.*
import logicOfBehavior.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.ui.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import com.soywiz.korim.font.*
import com.soywiz.korim.format.*
import com.soywiz.korio.async.*
import com.soywiz.korio.file.std.*


fun <T> drawS(element: T): T {
    element.apply { }
    return element
}

class GameView() : Scene() {
    override suspend fun SContainer.sceneInit() {
        val font = resourcesVfs["mr_countryhouseg_0.ttf"].readTtfFont(preload = false)
        val x0 = sceneContainer.width / 2
        val y0 = sceneContainer.height / 2
        var img = image(resourcesVfs["m_game_background_4.png"].readBitmap(), 0.5, 0.5) {
            x = x0
            y = y0
        }

        val heart = Health(resourcesVfs["health.png"].readBitmap())

        val spriteMapChest = resourcesVfs["AoM2.png"].readBitmap()
        val spriteMapRedPotion = resourcesVfs["Red Potion.png"].readBitmap()

        val spriteMapIdleDoll = resourcesVfs["doll/Idle.png"].readBitmap()
        val spriteMapWalk = resourcesVfs["doll/Walk_R.png"].readBitmap()
        val spriteMapOpenChest = resourcesVfs["doll/chest_opening_R.png"].readBitmap()
        val spriteMapAttack = resourcesVfs["doll/Attack_R.png"].readBitmap()
        val spriteMapDead = resourcesVfs["doll/Dead.png"].readBitmap()

        val spriteMapIdleMonster = resourcesVfs["pink/Pink_Monster_Idle_4.png"].readBitmap()
        val spriteMapHurt = resourcesVfs["pink/Pink_Monster_Hurt_4.png"].readBitmap()
        val spriteMapDeath = resourcesVfs["pink/Pink_Monster_Death_8.png"].readBitmap()
        val spriteMapWalkMonster = resourcesVfs["pink/Pink_Monster_Walk_Right.png"].readBitmap()
        val spriteMapMonsterAttack = resourcesVfs["pink/Pink_Monster_Attack1_4.png"].readBitmap()

        val loot = Loot(spriteMapRedPotion)
        val blueChest = Chest(spriteMapChest)
        val doll = Doll(
            spriteMapIdleDoll,
            spriteMapWalk,
            spriteMapOpenChest,
            spriteMapAttack,
            spriteMapDead
        )
        val pink =
            Pink(spriteMapIdleMonster, spriteMapHurt, spriteMapDeath, spriteMapWalkMonster, spriteMapMonsterAttack)

        val player = PlayerCharacter(
            doll.idleAnimation,
            doll.deadAnimation,
            doll.walkAnimation,
            1,
            5.0,
            doll.openChestAnimation,
            doll.attackAnimation
        )
        val chest = ChestAnimate(blueChest.idleAnimation, blueChest.openAnimation, 1)
        val redPotion = LootAnimate(loot.idleAnimation, sceneContainer)

        val monsterManager = MonsterManager(sceneContainer, player, pink)

        drawS(chest)
        chest.xy(rand(20, 450), rand(70, 250))

        drawS(player)
        player.xy(200, 200)

        addChild(chest)
        addChild(player)


        val playerManager = PlayerManager(sceneContainer, player)

        var numOfMonsters = 0
        when (MyModule.event) {
            1 -> numOfMonsters = 2
            2 -> numOfMonsters = 4
            3 -> numOfMonsters = 6
        }

        monsterManager.spawnMonster(numOfMonsters)
        monsterManager.drawMonsters()
        monsterManager.monsters.forEach { monster ->
            addChild(monster)
        }

        val livesManager = LivesManager(sceneContainer, player, heart)
        livesManager.spawnLives()
        livesManager.drawLives()
        livesManager.lives.forEach { live ->
            addChild(live)
        }

        fun update() {
            monsterManager.update()
            playerManager.update()
            chest.update(player, redPotion)
            livesManager.update()
        }

        update()

        //val title = Titles(sceneContainer, player, monsterManager, font)
        //title.addUpdater { title.update(it) }
//        addUpdater {
//            println(title.winOrLoose)
//            title.update(it)
//        }

        var checkout = 3.0
        addUpdater {
            if (monsterManager.monsters.size == 0 && !player.isDead) {
                MyModule.level += 1
                var winText = text("YOU WIN", 70.0, Colors.WHITE, font) {
                    position(views.virtualWidth / 2 - 100, views.virtualHeight / 2 - 60)
                }
            } else if (player.isDead && monsterManager.monsters.size > 0) {
                var looseText = text("YOU ARE DEAD", 70.0, Colors.WHITE, font) {
                    position(views.virtualWidth / 2 - 200, views.virtualHeight / 2 - 60)
                }
                if (checkout > 0) {
                    checkout -= it.seconds
                } else {
                    monsterManager.clearMonsters()
                    looseText.removeFromParent()
                    launch { sceneContainer.changeTo<GameMenu>() }
                }
            }
        }


        var menuButton = uiButton(100.0, 32.0) {
            text = "menu"
            uiSkin = UISkin {
                val colorTransform = ColorTransform(0.48, 0.83, 0.66)
                this.uiSkinBitmap = this.uiSkinBitmap.withColorTransform(colorTransform)
                this.buttonBackColor = this.buttonBackColor.transform(colorTransform)
                this.textFont = font
            }
            position(0, 0)
            onClick {
                sceneContainer.changeTo<GameMenu>()
            }
        }
    }
}
