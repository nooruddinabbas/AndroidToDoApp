package io.realm.androidtodoapp

import android.app.Application
import android.util.Log

import io.realm.Realm
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration

lateinit var toDoApp: App

class ToDoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val appId = "application-rt-lgapm"
        toDoApp = App(
            AppConfiguration.Builder(appId)
                .build())



        Log.v("Application", "Initialized the Realm App configuration for: ${toDoApp.configuration.appId}")
    }
}