package behavior

import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*
import kotlin.properties.*

class Hearts(baseValue: Int, private val icon: Bitmap, private val space: Double = 0.0) :
    Container() {
    var value by Delegates.vetoable(baseValue) { _, _, new ->
        if (new > children.size) {
            image(icon) {
                x += (children.size - 1) * (icon.width + space)
                println("+")
            }
        }
        if (new < children.size) {
            removeChild(children.last())
            println("-")
        }
        return@vetoable new > 0
    }

    init {
        for (i in 0 until value) {
            image(icon) {
                x += i * (icon.width + space)
            }
        }
    }
}



