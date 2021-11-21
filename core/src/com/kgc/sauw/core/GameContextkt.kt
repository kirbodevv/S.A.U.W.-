package com.kgc.sauw.core

import com.kgc.sauw.core.utils.IDManager

class GameContextkt {
    val SAUW = set("com.kgc.sauw", GameContextkt())

    val idManager = IDManager()

    companion object {
        private val gameContexts = mutableMapOf<String, GameContextkt>()

        @JvmStatic
        operator fun get(pkg: String) = gameContexts[pkg]!!

        @JvmStatic
        operator fun set(pkg: String, gameContext: GameContextkt) {
            gameContexts[pkg] = gameContext
        }
    }
}