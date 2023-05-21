import com.soywiz.klock.*
import com.soywiz.korev.*
import com.soywiz.korge.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.tween.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*
import com.soywiz.korma.geom.*
import com.soywiz.korma.interpolation.*
import com.soywiz.korge.animate.*
import com.soywiz.korge.input.*
import com.soywiz.korge.view.tween.*

import com.soywiz.korge.component.docking.*
import com.soywiz.korge.internal.*
import com.soywiz.korge.service.*
import com.soywiz.korinject.*
import screens.*


@OptIn(KorgeUntested::class)
suspend fun main() = Korge(Korge.Config(module = MyModule))

object MyModule : Module() {
    override val mainScene = Menu::class

    override val title: String = "My Game"
    override val size: SizeInt = SizeInt(560, 338)

    override suspend fun AsyncInjector.configure() {
        mapPrototype { Menu() }
        mapPrototype { GameView() }
        mapPrototype { GameMenu() }
        //mapPrototype { Play() }
    }

    var level = 1
    var event = 1
}
