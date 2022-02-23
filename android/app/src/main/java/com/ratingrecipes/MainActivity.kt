package com.ratingrecipes

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
import com.ratingrecipes.databinding.ActivityMainBinding

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
            minimumFetchIntervalInSeconds = 3600
        }
        firebaseRemoteConfig.setConfigSettingsAsync(configSettings)
//        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults

        binding.beatLevelOne.setOnClickListener {
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LEVEL_END) {
                param(FirebaseAnalytics.Param.LEVEL_NAME, "Level One")
                param("level_difficulty", 1)
                param(FirebaseAnalytics.Param.SUCCESS, "true")
            }

            showPrompt(1)
        }

        binding.beatLevelTwo.setOnClickListener {
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LEVEL_END) {
                param(FirebaseAnalytics.Param.LEVEL_NAME, "Level Two")
                param("level_difficulty", 2)
                param(FirebaseAnalytics.Param.SUCCESS, "true")
            }

            showPrompt(2)
        }

        binding.beatLevelThree.setOnClickListener {
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LEVEL_END) {
                param(FirebaseAnalytics.Param.LEVEL_NAME, "Level Three")
                param("level_difficulty", 3)
                param(FirebaseAnalytics.Param.SUCCESS, "true")
            }

            showPrompt(3)
        }

        binding.sentimentPrompt.setOnClickListener {
            showSentimentPrompt()
        }

        binding.inAppRating.setOnClickListener {
            showInAppRating()
        }

        binding.customRatingPrompt.setOnClickListener {
            firebaseAnalytics.logEvent("custom_rating_prompt_viewed", null)
            MaterialAlertDialogBuilder(this)
                .setMessage("Rate this app on Google Play?")
                .setNegativeButton("No") { dialog, which ->
                    firebaseAnalytics.logEvent("custom_rating_prompt_declined", null)
                }
                .setPositiveButton("Take me to Google Play") { dialog, which ->
                    firebaseAnalytics.logEvent("custom_rating_prompt_open_store", null)
                }
                .show()
        }
    }

    private fun showPrompt(currentLevel: Long) {
        val sentimentPromptLevel = firebaseRemoteConfig.getLong("sentiment_prompt_level")
        val inAppRatingLevel = firebaseRemoteConfig.getLong("in_app_rating_level")

        if (currentLevel == sentimentPromptLevel) {
            showSentimentPrompt()
        } else if (currentLevel == inAppRatingLevel) {
            showInAppRating()
        }
    }

    private fun showSentimentPrompt() {
        firebaseAnalytics.logEvent("sentiment_prompt_viewed", null)
        MaterialAlertDialogBuilder(this)
            .setMessage("Do you want enjoy this app?")
            .setNegativeButton("No") { dialog, which ->
                firebaseAnalytics.logEvent("sentiment_prompt_negative", null)
            }
            .setPositiveButton("Yes") { dialog, which ->
                firebaseAnalytics.logEvent("sentiment_prompt_positive", null)
            }
            .show()
    }

    private fun showInAppRating() {
        firebaseAnalytics.logEvent("in_app_rating_started", null)
        val manager = ReviewManagerFactory.create(applicationContext)
        val request = manager.requestReviewFlow()
        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val reviewInfo = task.result
                val flow = manager.launchReviewFlow(this, reviewInfo)
                flow.addOnCompleteListener { _ ->
                    firebaseAnalytics.logEvent("in_app_rating_complete", null)
                }
            } else {
                Toast.makeText(this, task.exception.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }
}