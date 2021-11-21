package com.kgc.sauw.core.utils

class IDManager {
    private class IDGroup(var lastID: Int = 0, val ids: MutableMap<String, Int> = mutableMapOf())

    companion object {
        private val groups = mutableMapOf<String, IDGroup>()
        fun newGroup(name: String) {
            groups[name] = IDGroup()
        }
    }

    fun newID(string: String) {
        val parts = string.split(":")
        val idGroup = groups[parts[0]] ?: throw NoSuchElementException("group ${parts[0]} is not found")
        idGroup.ids[parts[1]] = idGroup.lastID++
    }

    operator fun get(string: String): Int {
        val parts = string.split(":")
        val idGroup = groups[parts[0]] ?: throw NoSuchElementException("group ${parts[0]} is not found")
        return idGroup.ids[parts[1]] ?: throw NoSuchElementException("id $string is not found")
    }
}