package screens

import MyModule
import animate.*
import behavior.Character
import com.soywiz.kds.*
import com.soywiz.klock.*
import com.soywiz.korge.input.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.ui.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import com.soywiz.korim.font.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*

class ChooseYourFighter : Scene() {
    override suspend fun SContainer.sceneInit() {
        val x0 = sceneContainer.width / 2
        val y0 = sceneContainer.height / 2
        var img = image(resourcesVfs["scroll.png"].readBitmap(), 0.5, 0.5) {
            x = x0
            y = y0
        }
        val font = resourcesVfs["mr_countryhouseg_0.ttf"].readTtfFont(preload = false)

        val spriteMapIdleDoll = resourcesVfs["doll/Idle.png"].readBitmap()
        val spriteMapWalkDoll = resourcesVfs["doll/Walk_R.png"].readBitmap()
        val spriteMapOpenChestDoll = resourcesVfs["doll/chest_opening_R.png"].readBitmap()
        val spriteMapAttackDoll = resourcesVfs["doll/Attack_R.png"].readBitmap()
        val spriteMapDeadDoll = resourcesVfs["doll/Dead.png"].readBitmap()

        val spriteMapIdleKnight = resourcesVfs["knight/Idle.png"].readBitmap()
        val spriteMapDeadKnight = resourcesVfs["knight/Dead.png"].readBitmap()
        val spriteMapWalkKnight = resourcesVfs["knight/Walk.png"].readBitmap()

        val doll = Doll(
            spriteMapIdleDoll,
            spriteMapWalkDoll,
            spriteMapOpenChestDoll,
            spriteMapAttackDoll,
            spriteMapDeadDoll
        )

        val knight = Knight(
            spriteMapIdleKnight,
            spriteMapWalkKnight,
            spriteMapOpenChestDoll,
            spriteMapAttackDoll,
            spriteMapDeadKnight
        )

        val d = Character(doll.idleAnimation, doll.deadAnimation, doll.walkAnimation, 1, 4.0)

        val k = Character(knight.idleAnimation, knight.deadAnimation, knight.walkAnimation, 1, 4.0)

        d.apply {
            scale = 2.0
            xy(80, 100)
        }
        k.apply {
            scale = 2.0
            xy(370, 100)
        }

        addChild(d)
        addChild(k)

        d.playAnimationLooped(doll.idleAnimation, spriteDisplayTime = 150.milliseconds)
        k.playAnimationLooped(knight.idleAnimation, spriteDisplayTime = 200.milliseconds)

        var sceneText = text("Choose Your Fighter", 30.0, Colors.WHITE, font) {
            position(170, 15)
        }

        var dollButton = uiButton(100.0, 40.0) {
            text = "doll"
            uiSkin = UISkin {
                val colorTransform = ColorTransform(0.48, 0.83, 0.66)
                this.uiSkinBitmap = this.uiSkinBitmap.withColorTransform(colorTransform)
                this.buttonBackColor = this.buttonBackColor.transform(colorTransform)
                this.textSize = 20.0
                this.textFont = font
            }
            position(100, 260)
            onClick {
                MyModule.character = ChosenFighter.DOLL
                sceneContainer.changeTo<GameMenu>()
            }
        }
        var knightButton = uiButton(100.0, 40.0) {
            text = "knight"
            uiSkin = UISkin {
                val colorTransform = ColorTransform(0.48, 0.83, 0.66)
                this.uiSkinBitmap = this.uiSkinBitmap.withColorTransform(colorTransform)
                this.buttonBackColor = this.buttonBackColor.transform(colorTransform)
                this.textSize = 20.0
                this.textFont = font
            }
            position(385, 260)
            onClick {
                MyModule.character = ChosenFighter.KNIGHT
                sceneContainer.changeTo<GameMenu>()
            }
        }
    }
}
