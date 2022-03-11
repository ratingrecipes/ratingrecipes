package com.ratingrecipes.internal

import android.content.Context
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ratingrecipes.R
import com.ratingrecipes.RatingRecipes
import java.lang.ref.WeakReference

internal class RatingRecipesImpl {
    var enableDebug = false
    private var recipeId = ""
    private var enableInAppRating = false

    private var context: WeakReference<Context> = WeakReference(null)

    fun configure(context: Context) {
        this.context = WeakReference(context.applicationContext)
    }

    fun getRecipe(id: String): RatingRecipes.Recipe {
        if (enableDebug) {
            context.get()?.let {
                Toast.makeText(
                    it,
                    "Recipe: $id Target: $recipeId, enableInAppRating: $enableInAppRating ",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return if (id == recipeId) {
            if (enableInAppRating) {
                Sentiment(
                    message = context.get()?.getString(R.string.rr_sentiment_prompt_message) ?: "",
                    negativeText = context.get()
                        ?.getString(R.string.rr_sentiment_prompt_negative_text) ?: "",
                    positiveText = context.get()
                        ?.getString(R.string.rr_sentiment_prompt_positive_text) ?: ""
                )
            } else {
                InAppRating
            }
        } else {
            None
        }
    }

    fun cook(recipe: RatingRecipes.Recipe) {
        when (recipe) {
            is RatingRecipes.Recipe.InAppRating -> showInAppRating(recipe)
            is RatingRecipes.Recipe.Sentiment -> showSentimentPrompt(recipe)
            is RatingRecipes.Recipe.None -> {
                // Do nothing
            }
        }
    }


    private fun showSentimentPrompt(sentiment: RatingRecipes.Recipe.Sentiment) {
        firebaseAnalytics.logEvent("rr_sentiment_prompt_view", null)
        MaterialAlertDialogBuilder(this)
            .setMessage(sentiment.message)
            .setNegativeButton(sentiment.negativeText) { dialog, which ->
                firebaseAnalytics.logEvent("rr_sentiment_prompt_negative", null)
            }
            .setPositiveButton(sentiment.positiveText) { dialog, which ->
                firebaseAnalytics.logEvent("rr_sentiment_prompt_positive", null)
            }
            .show()
    }

    private fun showInAppRating(inAppRating: Recipe.InAppRating) {
        if (enableDebug) {
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
                    if (enableDebug) {
                        Toast.makeText(this, "In-App Rating Complete", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                firebaseAnalytics.logEvent("rr_in_app_rating_failure", null)
                if (enableDebug) {
                    Toast.makeText(this, task.exception.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}