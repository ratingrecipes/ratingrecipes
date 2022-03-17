package com.ratingrecipes.internal

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.play.core.review.ReviewManagerFactory
import com.ratingrecipes.R
import com.ratingrecipes.RatingRecipes
import com.ratingrecipes.internal.IngredientsImpl.enableInAppRating
import com.ratingrecipes.internal.IngredientsImpl.logEvent
import com.ratingrecipes.internal.IngredientsImpl.recipeId

internal class RatingRecipesImpl {
    var debug = false

    private val tag = "RatingRecipes"
    private var ingredients: RatingRecipes.Ingredients? = null
    private val isPrepared: Boolean
        get() = (ingredients != null).also {
            if (!it) logAndToast("Missing call to RatingRecipes.prepare(ingredients)")
        }

    fun prepare(ingredients: RatingRecipes.Ingredients) {
        this.ingredients = ingredients
    }

    fun getRecipe(id: String): RatingRecipes.Recipe {
        if (!isPrepared) return None

        return ingredients?.let { ingredients ->
            val recipeId = ingredients.recipeId
            val enableInAppRating = ingredients.enableInAppRating
            logAndToast("Recipe: $id, Target: $recipeId, enableInAppRating: $enableInAppRating")
            if (id == recipeId) {
                if (enableInAppRating) {
                    InAppRating
                } else {
                    Sentiment(
                        message = ingredients.applicationContext.getString(R.string.rr_sentiment_prompt_message),
                        negativeText = ingredients.applicationContext.getString(R.string.rr_sentiment_prompt_negative_text),
                        positiveText = ingredients.applicationContext.getString(R.string.rr_sentiment_prompt_positive_text)
                    )
                }
            } else {
                None
            }
        } ?: None
    }

    fun cook(activity: Activity, recipe: RatingRecipes.Recipe) {
        if (!isPrepared) return

        ingredients?.let { ingredients ->
            when (recipe) {
                is RatingRecipes.Recipe.InAppRating -> showInAppRating(
                    activity,
                    ingredients
                )
                is RatingRecipes.Recipe.Sentiment -> showSentimentPrompt(
                    activity,
                    ingredients,
                    recipe
                )
                is RatingRecipes.Recipe.None -> {
                    // Do nothing
                }
            }
        }
    }

    private fun showSentimentPrompt(
        activity: Activity,
        ingredients: RatingRecipes.Ingredients,
        sentiment: RatingRecipes.Recipe.Sentiment
    ) {
        ingredients.logEvent("rr_sentiment_prompt_view")
        MaterialAlertDialogBuilder(activity)
            .setMessage(sentiment.message)
            .setNegativeButton(sentiment.negativeText) { _, _ ->
                ingredients.logEvent("rr_sentiment_prompt_negative")
            }
            .setPositiveButton(sentiment.positiveText) { _, _ ->
                ingredients.logEvent("rr_sentiment_prompt_positive")
            }
            .show()
    }

    private fun showInAppRating(
        activity: Activity,
        ingredients: RatingRecipes.Ingredients
    ) {
        logAndToast("In-App Rating Requested")

        ingredients.logEvent("rr_in_app_rating_start")
        val manager = ReviewManagerFactory.create(ingredients.applicationContext)
        val request = manager.requestReviewFlow()
        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val reviewInfo = task.result
                val flow = manager.launchReviewFlow(activity, reviewInfo)
                flow.addOnCompleteListener { _ ->
                    ingredients.logEvent("rr_in_app_rating_complete")
                    logAndToast("In-App Rating Complete")
                }
            } else {
                ingredients.logEvent("rr_in_app_rating_failure")
                logAndToast("In-App Rating Failure: ${task.exception.toString()}")
            }
        }
    }

    private fun logAndToast(message: String) {
        if (debug) {
            Log.d(tag, message)
            ingredients?.applicationContext?.let {
                Toast.makeText(
                    it,
                    message,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}