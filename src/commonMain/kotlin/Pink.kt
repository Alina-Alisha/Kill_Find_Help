import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*

class Pink(
    spriteMapIdle: Bitmap,
    spriteMapHurt: Bitmap,
    spriteMapDeath: Bitmap,
    spriteMapWalkRightMonster: Bitmap
) {

    val idleAnimation = SpriteAnimation(
        spriteMap = spriteMapIdle,
        spriteWidth = 32,
        spriteHeight = 32,
        marginTop = 0,
        marginLeft = 0,
        columns = 4,
        rows = 1,
        offsetBetweenColumns = 0,
        offsetBetweenRows = 0,
    )

    val hurtAnimation = SpriteAnimation(
        spriteMap = spriteMapHurt,
        spriteWidth = 32,
        spriteHeight = 32,
        marginTop = 0,
        marginLeft = 0,
        columns = 4,
        rows = 1,
        offsetBetweenColumns = 0,
        offsetBetweenRows = 0,
    )
    val deathAnimation = SpriteAnimation(
        spriteMap = spriteMapDeath,
        spriteWidth = 32,
        spriteHeight = 32,
        marginTop = 0,
        marginLeft = 0,
        columns = 4,
        rows = 1,
        offsetBetweenColumns = 0,
        offsetBetweenRows = 0,
    )
    val walkAnimation = SpriteAnimation(
        spriteMap = spriteMapWalkRightMonster,
        spriteWidth = 32,
        spriteHeight = 32,
        marginTop = 0,
        marginLeft = 0,
        columns = 4,
        rows = 1,
        offsetBetweenColumns = 0,
        offsetBetweenRows = 0,
    )
}
