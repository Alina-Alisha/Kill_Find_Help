import com.soywiz.korim.bitmap.*

class Chest (spriteMapChest: Bitmap){
    val idleAnimation = com.soywiz.korge.view.SpriteAnimation(
        spriteMap = spriteMapChest,
        spriteWidth = 70,
        spriteHeight = 54,
        marginTop = 0,
        marginLeft = 0,
        columns = 1,
        rows = 1,
        offsetBetweenColumns = 0,
        offsetBetweenRows = 0,
    )

//    fun animate() {
//        playAnimation(spriteAnimation = idleAnimation)
//    }
}
