import com.soywiz.klock.*
import com.soywiz.korev.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import com.soywiz.korge.view.tween.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*


class Play() : Scene() {
    override suspend fun SContainer.sceneInit() {
        //readImage("game_background_4.jpg")).dockedTo(Anchor.TOP_LEFT, ScaleMode.EXACT)

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
            xy(200, 300)
        }

//        val monsters = Array(2) {
//            Monster(pink.idleAnimation).apply {
//                scale(2)
//                xy(450, 350)
//                //moveBy(0.0, 25.0, 1.seconds)
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
//        while (true) {
//            monsters.forEach { it ->
//                val monster = it
//                addChild(monster)
//                monster.addUpdater { monster.animate(pink.idleAnimation, pink.walkAnimation, it) }
//                monster.onCollision({ it == player && player.isAttacking }) {
//                    monster.hurt(
//                        pink.hurtAnimation,
//                        pink.deathAnimation
//                    )
//                    //visible = false
//                    player.isAttacking = false
//                    monster.isHurt = false
//                }
//            }
//        }

        val monster = Monster(pink.idleAnimation).apply {
            scale(2)
            xy(250, 350)
        }

        addChild(monster)

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

