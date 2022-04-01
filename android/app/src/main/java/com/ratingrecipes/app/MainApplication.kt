package com.ratingrecipes.app

import android.app.Application
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.ratingrecipes.RatingRecipes

class MainApplication : Application() {

    private lateinit var firebaseRemoteConfig: FirebaseRemoteConfig


    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            firebaseRemoteConfig = Firebase.remoteConfig
            val configSettings = remoteConfigSettings {
                minimumFetchIntervalInSeconds = 5
            }
            firebaseRemoteConfig.setConfigSettingsAsync(configSettings)
        }
        firebaseRemoteConfig.fetchAndActivate()

        RatingRecipes.debug = BuildConfig.DEBUG

        RatingRecipes.prepare(
            RatingRecipes.Ingredients.Firebase(
                applicationContext = applicationContext,
                firebase = Firebase
            )
        )
    }
}