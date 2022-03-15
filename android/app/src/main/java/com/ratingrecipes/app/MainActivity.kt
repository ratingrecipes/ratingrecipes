package com.ratingrecipes.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.ratingrecipes.RatingRecipes
import com.ratingrecipes.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var firebaseRemoteConfig: FirebaseRemoteConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        firebaseAnalytics = Firebase.analytics

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

        binding.beatLevelOne.setOnClickListener {
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LEVEL_END) {
                param(FirebaseAnalytics.Param.LEVEL_NAME, "Level One")
                param("level_difficulty", 1)
                param(FirebaseAnalytics.Param.SUCCESS, "true")
            }

            getAndHandleRecipe("level_one")
        }

        binding.beatLevelTwo.setOnClickListener {
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LEVEL_END) {
                param(FirebaseAnalytics.Param.LEVEL_NAME, "Level Two")
                param("level_difficulty", 2)
                param(FirebaseAnalytics.Param.SUCCESS, "true")
            }

            getAndHandleRecipe("level_two")
        }

        binding.beatLevelThree.setOnClickListener {
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LEVEL_END) {
                param(FirebaseAnalytics.Param.LEVEL_NAME, "Level Three")
                param("level_difficulty", 3)
                param(FirebaseAnalytics.Param.SUCCESS, "true")
            }

            getAndHandleRecipe("level_three")
        }
    }

    fun getAndHandleRecipe(recipeId: String) {
        when (val recipe = RatingRecipes.getRecipe("level_three")) {
            is RatingRecipes.Recipe.InAppRating -> RatingRecipes.cook(this, recipe)
            is RatingRecipes.Recipe.Sentiment -> RatingRecipes.cook(this, recipe)
            is RatingRecipes.Recipe.None -> {
                // Nothing to do
            }
        }
    }
}