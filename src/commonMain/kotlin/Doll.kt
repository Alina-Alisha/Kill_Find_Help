import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*

class Doll(
    spriteMapIdle: Bitmap,
    spriteMapWalkRight: Bitmap,
    spriteMapWalkLeft: Bitmap,
    spriteMapOpenChest: Bitmap,
    spriteMapAttack: Bitmap
) {

    val idleAnimation = SpriteAnimation(
        spriteMap = spriteMapIdle,
        spriteWidth = 128,
        spriteHeight = 128,
        marginTop = 0,
        marginLeft = 0,
        columns = 5,
        rows = 1,
        offsetBetweenColumns = 0,
        offsetBetweenRows = 0,
    )

    val walkRightAnimation = SpriteAnimation(
        spriteMap = spriteMapWalkRight,
        spriteWidth = 128,
        spriteHeight = 128,
        marginTop = 0,
        marginLeft = 0,
        columns = 5,
        rows = 1,
        offsetBetweenColumns = 0,
        offsetBetweenRows = 0,
    )
    val walkLeftAnimation = SpriteAnimation(
        spriteMap = spriteMapWalkLeft,
        spriteWidth = 128,
        spriteHeight = 128,
        marginTop = 0,
        marginLeft = 0,
        columns = 5,
        rows = 1,
        offsetBetweenColumns = 0,
        offsetBetweenRows = 0,
    )
    val openChestAnimation = SpriteAnimation(
        spriteMap = spriteMapOpenChest,
        spriteWidth = 128,
        spriteHeight = 128,
        marginTop = 0,
        marginLeft = 0,
        columns = 10,
        rows = 1,
        offsetBetweenColumns = 0,
        offsetBetweenRows = 0,
    )
    val attackAnimation = SpriteAnimation(
        spriteMap = spriteMapAttack,
        spriteWidth = 128,
        spriteHeight = 128,
        marginTop = 0,
        marginLeft = 0,
        columns = 3,
        rows = 1,
        offsetBetweenColumns = 0,
        offsetBetweenRows = 0,
    )
}

