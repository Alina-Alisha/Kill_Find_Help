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
import com.soywiz.korio.file.std.*


fun <T> drawS(element: T): T {
    element.apply {  }
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
        val spriteMapWalkRight = resourcesVfs["doll/Walk_R.png"].readBitmap()
        val spriteMapWalkLeft = resourcesVfs["doll/Walk_L.png"].readBitmap()
        val spriteMapOpenChestRight = resourcesVfs["doll/chest_opening_R.png"].readBitmap()
        val spriteMapOpenChestLeft = resourcesVfs["doll/chest_opening_L.png"].readBitmap()
        val spriteMapAttackRight = resourcesVfs["doll/Attack_R.png"].readBitmap()
        val spriteMapDead = resourcesVfs["doll/Dead.png"].readBitmap()

        val spriteMapIdleMonster = resourcesVfs["pink/Pink_Monster_Idle_4.png"].readBitmap()
        val spriteMapHurt = resourcesVfs["pink/Pink_Monster_Hurt_4.png"].readBitmap()
        val spriteMapDeath = resourcesVfs["pink/Pink_Monster_Death_8.png"].readBitmap()
        val spriteMapWalkRightMonster = resourcesVfs["pink/Pink_Monster_Walk_Right.png"].readBitmap()
        val spriteMapMonsterAttack = resourcesVfs["pink/Pink_Monster_Attack1_4.png"].readBitmap()

        val loot = Loot(spriteMapRedPotion)
        val blueChest = Chest(spriteMapChest)
        val doll = Doll(
            spriteMapIdleDoll,
            spriteMapWalkRight,
            spriteMapWalkLeft,
            spriteMapOpenChestRight,
            spriteMapOpenChestLeft,
            spriteMapAttackRight,
            spriteMapDead
        )
        val pink =
            Pink(spriteMapIdleMonster, spriteMapHurt, spriteMapDeath, spriteMapWalkRightMonster, spriteMapMonsterAttack)

        val player = PlayerCharacter(
            doll.idleAnimation,
            doll.deadAnimation,
            doll.walkRightAnimation,
            1,
            5.0,
            doll.openChestAnimationRight,
            doll.attackAnimationRight
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


        val playerManager = PlayerManager(sceneContainer, player, chest)

        var numOfMonsters = 0
        when (MyModule.event) {
            1 -> numOfMonsters = 2
            2 -> numOfMonsters = 4
            3 -> numOfMonsters = 6
        }

        monsterManager.spawnMonster(numOfMonsters)
        monsterManager.drawMonsters()

        val livesManager = LivesManager(sceneContainer, player, heart)
        livesManager.spawnLives()
        //livesManager.drawLives()


        fun update() {
            monsterManager.update()
            playerManager.update()
            chest.update(player, redPotion)
            livesManager.update()
        }

        update()

        var menuButton = uiButton(100.0, 32.0) {
            text = "menu"
            uiSkin = UISkin {
                val colorTransform = ColorTransform(0.48, 0.83, 0.66)
                this.uiSkinBitmap = this.uiSkinBitmap.withColorTransform(colorTransform)
                this.buttonBackColor = this.buttonBackColor.transform(colorTransform)
                //this.textSize = 30.0
                this.textFont = font
            }
            position(0, 0)
            onClick {
                sceneContainer.changeTo<GameMenu>()
            }
        }
    }
}
