package animate

import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*

class Knight(
    spriteMapIdle: Bitmap,
    spriteMapWalk: Bitmap,
    spriteMapOpenChest: Bitmap,
    spriteMapAttack: Bitmap,
    spriteMapDead: Bitmap
): CharacterSprite(spriteMapIdle, spriteMapWalk, spriteMapOpenChest, spriteMapAttack, spriteMapDead) {
    override val idleAnimation = SpriteAnimation(
        spriteMap = spriteMapIdle,
        spriteWidth = 71,
        spriteHeight = 73,
        marginTop = 0,
        marginLeft = 0,
        columns = 6,
        rows = 1,
        offsetBetweenColumns = 0,
        offsetBetweenRows = 0,
    )

    override val walkAnimation = SpriteAnimation(
        spriteMap = spriteMapWalk,
        spriteWidth = 454/8,
        spriteHeight = 73,
        marginTop = 0,
        marginLeft = 0,
        columns = 8,
        rows = 1,
        offsetBetweenColumns = 0,
        offsetBetweenRows = 0,
    )
    override val openChestAnimation = SpriteAnimation(
        spriteMap = spriteMapOpenChest,
        spriteWidth = 78,
        spriteHeight = 73,
        marginTop = 0,
        marginLeft = 0,
        columns = 2,
        rows = 1,
        offsetBetweenColumns = 0,
        offsetBetweenRows = 0,
    )

    override val attackAnimation = SpriteAnimation(
        spriteMap = spriteMapAttack,
        spriteWidth = 72,
        spriteHeight = 73,
        marginTop = 0,
        marginLeft = 0,
        columns = 5,
        rows = 1,
        offsetBetweenColumns = 1,
        offsetBetweenRows = 0,
    )
override val deadAnimation = SpriteAnimation(
        spriteMap = spriteMapDead,
        spriteWidth = 73,
        spriteHeight = 73,
        marginTop = 0,
        marginLeft = 0,
        columns = 4,
        rows = 1,
        offsetBetweenColumns = 0,
        offsetBetweenRows = 0,
    )
}
