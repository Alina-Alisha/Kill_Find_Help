package animate

import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*

open class CharacterSprite(
    spriteMapIdle: Bitmap,
    spriteMapWalk: Bitmap,
    spriteMapOpenChest: Bitmap,
    spriteMapAttack: Bitmap,
    spriteMapDead: Bitmap
) {
    open val idleAnimation = SpriteAnimation(
        spriteMap = spriteMapIdle,
        spriteWidth = 56,
        spriteHeight = 73,
        marginTop = 0,
        marginLeft = 0,
        columns = 5,
        rows = 1,
        offsetBetweenColumns = 0,
        offsetBetweenRows = 0,
    )

    open val walkAnimation = SpriteAnimation(
        spriteMap = spriteMapWalk,
        spriteWidth = 56,
        spriteHeight = 73,
        marginTop = 0,
        marginLeft = 0,
        columns = 5,
        rows = 1,
        offsetBetweenColumns = 0,
        offsetBetweenRows = 0,
    )
    open val openChestAnimation = SpriteAnimation(
        spriteMap = spriteMapOpenChest,
        spriteWidth = 128,
        spriteHeight = 73,
        marginTop = 0,
        marginLeft = 0,
        columns = 10,
        rows = 1,
        offsetBetweenColumns = 0,
        offsetBetweenRows = 0,
    )

    open val attackAnimation = SpriteAnimation(
        spriteMap = spriteMapAttack,
        spriteWidth = 128,
        spriteHeight = 73,
        marginTop = 0,
        marginLeft = 0,
        columns = 3,
        rows = 1,
        offsetBetweenColumns = 0,
        offsetBetweenRows = 0,
    )
    open val deadAnimation = SpriteAnimation(
        spriteMap = spriteMapDead,
        spriteWidth = 128,
        spriteHeight = 76,
        marginTop = 0,
        marginLeft = 0,
        columns = 5,
        rows = 1,
        offsetBetweenColumns = 0,
        offsetBetweenRows = 0,
    )
}
