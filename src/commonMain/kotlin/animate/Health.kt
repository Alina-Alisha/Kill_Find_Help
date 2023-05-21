package animate

import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*

class Health(
    spriteMapIdle: Bitmap,
) {
    val idleAnimation = SpriteAnimation(
        spriteMap = spriteMapIdle,
        spriteWidth = 33,
        spriteHeight = 30,
        marginTop = 0,
        marginLeft = 0,
        columns = 1,
        rows = 1,
        offsetBetweenColumns = 0,
        offsetBetweenRows = 0,
    )

    val deadAnimation = SpriteAnimation(
        spriteMap = spriteMapIdle,
        spriteWidth = 33,
        spriteHeight = 30,
        marginTop = 0,
        marginLeft = 33,
        columns = 1,
        rows = 1,
        offsetBetweenColumns = 0,
        offsetBetweenRows = 0,
    )

    val dyingAnimation = SpriteAnimation(
        spriteMap = spriteMapIdle,
        spriteWidth = 33,
        spriteHeight = 30,
        marginTop = 0,
        marginLeft = 0,
        columns = 2,
        rows = 1,
        offsetBetweenColumns = 0,
        offsetBetweenRows = 0,
    )
}
