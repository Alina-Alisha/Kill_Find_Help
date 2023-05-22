package animate

import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*

class Doll(
    spriteMapIdle: Bitmap,
    spriteMapWalkRight: Bitmap,
    spriteMapWalkLeft: Bitmap,
    spriteMapOpenChestRight: Bitmap,
    spriteMapOpenChestLeft: Bitmap,
    spriteMapAttackRight: Bitmap,
    spriteMapDead: Bitmap
) {
    val idleAnimation = SpriteAnimation(
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

    val walkRightAnimation = SpriteAnimation(
        spriteMap = spriteMapWalkRight,
        spriteWidth = 56,
        spriteHeight = 73,
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
    val openChestAnimationRight = SpriteAnimation(
        spriteMap = spriteMapOpenChestRight,
        spriteWidth = 128,
        spriteHeight = 73,
        marginTop = 0,
        marginLeft = 0,
        columns = 10,
        rows = 1,
        offsetBetweenColumns = 0,
        offsetBetweenRows = 0,
    )
    val openChestAnimationLeft = SpriteAnimation(
        spriteMap = spriteMapOpenChestLeft,
        spriteWidth = 128,
        spriteHeight = 128,
        marginTop = 0,
        marginLeft = 0,
        columns = 10,
        rows = 1,
        offsetBetweenColumns = 0,
        offsetBetweenRows = 0,
    )
    val attackAnimationRight = SpriteAnimation(
        spriteMap = spriteMapAttackRight,
        spriteWidth = 128,
        spriteHeight = 73,
        marginTop = 0,
        marginLeft = 0,
        columns = 3,
        rows = 1,
        offsetBetweenColumns = 0,
        offsetBetweenRows = 0,
    )
    val deadAnimation = SpriteAnimation(
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

