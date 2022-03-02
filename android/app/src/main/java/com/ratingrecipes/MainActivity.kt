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

    private var sentimentPromptLevel = ""
    private var inAppRatingLevel = ""

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
            sentimentPromptLevel = firebaseRemoteConfig.getString("rr_sentiment_prompt_location")
            inAppRatingLevel = firebaseRemoteConfig.getString("rr_in_app_rating_location")

            binding.sentimentPromptLevel.text = "Sentiment Prompt Level: $sentimentPromptLevel"
            binding.inAppRatingLevel.text = "In-App Rating Level: $inAppRatingLevel"
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

        binding.beatLevelFour.setOnClickListener {
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LEVEL_END) {
                param(FirebaseAnalytics.Param.LEVEL_NAME, "Level Four")
                param("level_difficulty", 4)
                param(FirebaseAnalytics.Param.SUCCESS, "true")
            }

            showPrompt("level_four")
        }

        binding.beatLevelFive.setOnClickListener {
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LEVEL_END) {
                param(FirebaseAnalytics.Param.LEVEL_NAME, "Level Five")
                param("level_difficulty", 5)
                param(FirebaseAnalytics.Param.SUCCESS, "true")
            }

            showPrompt("level_five")
        }
    }

    private fun showPrompt(currentLevel: String) {
        if (currentLevel == sentimentPromptLevel) {
            showSentimentPrompt()
        } else if (currentLevel == inAppRatingLevel) {
            showInAppRating()
        }
    }

    private fun showSentimentPrompt() {
        firebaseAnalytics.logEvent("rr_sentiment_prompt_view", null)
        MaterialAlertDialogBuilder(this)
            .setMessage("Loving our app?")
            .setNegativeButton("Nope") { dialog, which ->
                firebaseAnalytics.logEvent("rr_sentiment_prompt_negative", null)
            }
            .setPositiveButton("Heck, yeah!") { dialog, which ->
                firebaseAnalytics.logEvent("rr_sentiment_prompt_positive", null)
            }
            .show()
    }

    private fun showInAppRating() {
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