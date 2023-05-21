package animate

import com.soywiz.korim.bitmap.*

class Chest(spriteMapChest: Bitmap) {
    val idleAnimation = com.soywiz.korge.view.SpriteAnimation(
        spriteMap = spriteMapChest,
        spriteWidth = 100,
        spriteHeight = 100,
        marginTop = 0,
        marginLeft = 0,
        columns = 1,
        rows = 1,
        offsetBetweenColumns = 0,
        offsetBetweenRows = 0,
    )

    val openAnimation = com.soywiz.korge.view.SpriteAnimation(
        spriteMap = spriteMapChest,
        spriteWidth = 100,
        spriteHeight = 100,
        marginTop = 0,
        marginLeft = 0,
        columns = 3,
        rows = 1,
        offsetBetweenColumns = 0,
        offsetBetweenRows = 0,
    )

}
