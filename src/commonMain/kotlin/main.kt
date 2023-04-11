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


@OptIn(KorgeUntested::class)
suspend fun main() = Korge(Korge.Config(module = MyModule))

object MyModule : Module() {
    // define the opening scene
    override val mainScene = Menu::class

    // define the game configs

    override val title: String = "My Game"
    override val size: SizeInt = SizeInt(512, 512)

    // add the scenes to the module
    override suspend fun AsyncInjector.configure() {
        mapPrototype { Menu() }
        mapPrototype { Play() }
    }
}