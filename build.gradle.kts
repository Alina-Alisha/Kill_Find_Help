import com.soywiz.korge.gradle.*

plugins {
    alias(libs.plugins.korge)
}

korge {
    id = "com.game.fantasymaidens"
    name = "fantasymaidens"
    targetJvm()
    targetJs()

    jvmMainClassName = "MainKt"
}

