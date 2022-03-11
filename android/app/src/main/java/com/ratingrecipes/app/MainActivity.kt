package com.ratingrecipes.app

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
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

        firebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 5
        }
        firebaseRemoteConfig.setConfigSettingsAsync(configSettings)
//        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

        firebaseRemoteConfig.fetchAndActivate().addOnCompleteListener {
            recipeId = firebaseRemoteConfig.getString("rr_recipe_id")
            enableInAppRating = firebaseRemoteConfig.getBoolean("rr_enable_in_app_rating")

            binding.sentimentPromptLevel.text = "Recipe Id: $recipeId"
            binding.inAppRatingLevel.text = "Enable In-App Rating: $enableInAppRating"
        }

        binding.beatLevelOne.setOnClickListener {
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LEVEL_END) {
                param(FirebaseAnalytics.Param.LEVEL_NAME, "Level One")
                param("level_difficulty", 1)
                param(FirebaseAnalytics.Param.SUCCESS, "true")
            }

            getRecipe("level_one")
        }

        binding.beatLevelTwo.setOnClickListener {
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LEVEL_END) {
                param(FirebaseAnalytics.Param.LEVEL_NAME, "Level Two")
                param("level_difficulty", 2)
                param(FirebaseAnalytics.Param.SUCCESS, "true")
            }

            getRecipe("level_two")
        }

        binding.beatLevelThree.setOnClickListener {
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LEVEL_END) {
                param(FirebaseAnalytics.Param.LEVEL_NAME, "Level Three")
                param("level_difficulty", 3)
                param(FirebaseAnalytics.Param.SUCCESS, "true")
            }

            getRecipe("level_three")
        }
    }
}