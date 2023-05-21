package animate

import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*

class Loot(spriteMapIdle: Bitmap) {
    val idleAnimation = SpriteAnimation(
        spriteMap = spriteMapIdle,
        spriteWidth = 16,
        spriteHeight = 16,
        marginTop = 0,
        marginLeft = 0,
        columns = 1,
        rows = 1,
        offsetBetweenColumns = 0,
        offsetBetweenRows = 0,
    )

}
