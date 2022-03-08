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

    private var promptLocation = ""
    private var enableInAppRating = false

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
            promptLocation = firebaseRemoteConfig.getString("rr_prompt_location")
            enableInAppRating = firebaseRemoteConfig.getBoolean("rr_enable_in_app_rating")

            binding.sentimentPromptLevel.text = "Prompt Location: $promptLocation"
            binding.inAppRatingLevel.text = "Enable In-App Rating: $enableInAppRating"
        }

        binding.beatLevelOne.setOnClickListener {
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LEVEL_END) {
                param(FirebaseAnalytics.Param.LEVEL_NAME, "Level One")
                param("level_difficulty", 1)
                param(FirebaseAnalytics.Param.SUCCESS, "true")
            }

            showPrompt("level_one")
        }

        binding.beatLevelTwo.setOnClickListener {
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LEVEL_END) {
                param(FirebaseAnalytics.Param.LEVEL_NAME, "Level Two")
                param("level_difficulty", 2)
                param(FirebaseAnalytics.Param.SUCCESS, "true")
            }

            showPrompt("level_two")
        }

        binding.beatLevelThree.setOnClickListener {
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LEVEL_END) {
                param(FirebaseAnalytics.Param.LEVEL_NAME, "Level Three")
                param("level_difficulty", 3)
                param(FirebaseAnalytics.Param.SUCCESS, "true")
            }

            showPrompt("level_three")
        }
    }

    private fun showPrompt(currentLevel: String) {
        if (currentLevel == promptLocation) {
            if (enableInAppRating) {
                showInAppRating(showDebugToast = true)
            } else {
                showSentimentPrompt()
            }
        }
    }

    private fun showSentimentPrompt() {
        firebaseAnalytics.logEvent("rr_sentiment_prompt_view", null)
        MaterialAlertDialogBuilder(this)
            .setMessage("Do you love our game?")
            .setNegativeButton("Nope") { dialog, which ->
                firebaseAnalytics.logEvent("rr_sentiment_prompt_negative", null)
            }
            .setPositiveButton("Heck, yeah!") { dialog, which ->
                firebaseAnalytics.logEvent("rr_sentiment_prompt_positive", null)
            }
            .show()
    }

    private fun showInAppRating(showDebugToast: Boolean = false) {
        if (showDebugToast) {
            Toast.makeText(this, "In-App Rating Requested", Toast.LENGTH_SHORT).show()
        }

        firebaseAnalytics.logEvent("rr_in_app_rating_start", null)
        val manager = ReviewManagerFactory.create(applicationContext)
        val request = manager.requestReviewFlow()
        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val reviewInfo = task.result
                val flow = manager.launchReviewFlow(this, reviewInfo)
                flow.addOnCompleteListener { _ ->
                    firebaseAnalytics.logEvent("rr_in_app_rating_complete", null)
                }
            } else {
                firebaseAnalytics.logEvent("rr_in_app_rating_failure", null)
                Toast.makeText(this, task.exception.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }
}