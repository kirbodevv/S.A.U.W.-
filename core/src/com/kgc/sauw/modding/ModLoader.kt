package com.kgc.sauw.modding

import com.badlogic.gdx.files.FileHandle
import com.kgc.sauw.core.GameContext
import com.kgc.sauw.core.achievements.Achievements
import com.kgc.sauw.core.achievements.JSChecker
import com.kgc.sauw.core.item.Items
import com.kgc.sauw.core.utils.Variables
import com.kgc.sauw.core.utils.languages.Languages
import com.kgc.sauw.modding.json_data.JSONAchievement
import com.kgc.sauw.modding.json_data.JSONItem
import org.json.JSONObject

fun loadItems(modItemsDir: FileHandle, gameContext: GameContext) {
    val files: Array<FileHandle> = modItemsDir.list()
    for (file in files) {
        if (file.name().endsWith(".item.json")) {
            val jsonItem = JSONItem()
            jsonItem.parse(JSONObject(file.readString()))
            val item = jsonItem.toObject()
            Items.registry.register(item, gameContext.getPackage(), jsonItem.id)
        }
    }
}

fun loadResources(modResourcesDir: FileHandle) {
    val resourceVariables = JSONObject(modResourcesDir.child("resources.json").readString())
    for (key in resourceVariables.keySet()) {
        val resource = modResourcesDir.child(resourceVariables.getString(key))
        Variables.putVariable(key, resource.path())
    }
}

fun loadLocalizations(modLocalizationDir: FileHandle) {
    val files = modLocalizationDir.list()
    for (file in files) {
        if (file.name().endsWith(".language")) Languages.loadLanguage(file, file.nameWithoutExtension())
    }
}

fun loadAchievements(achievementsDir: FileHandle, modScripts: ModScripts, gameContext: GameContext) {
    val files = achievementsDir.list()
    for (file in files) {
        if (file.name().endsWith(".achievement.json")) {
            val jsonAchievement = JSONAchievement()
            jsonAchievement.parse(JSONObject(file.readString()))
            val achievement = jsonAchievement.toObject()
            achievement.achievementChecker = JSChecker(modScripts.getScript(jsonAchievement.script), jsonAchievement.id)
            Achievements.registry.register(achievement, gameContext.getPackage(), jsonAchievement.id)
        }
    }
}