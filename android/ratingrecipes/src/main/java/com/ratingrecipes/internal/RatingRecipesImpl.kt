package com.ratingrecipes.internal

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.play.core.review.ReviewManagerFactory
import com.ratingrecipes.R
import com.ratingrecipes.RatingRecipes
import com.ratingrecipes.internal.IngredientsImpl.enableInAppRating
import com.ratingrecipes.internal.IngredientsImpl.log
import com.ratingrecipes.internal.IngredientsImpl.recipeId
import java.lang.ref.WeakReference

internal class RatingRecipesImpl {
    var debug = false

    private val tag = "RatingRecipes"
    private var ingredients: WeakReference<RatingRecipes.Ingredients> = WeakReference(null)

    fun prepare(ingredients: RatingRecipes.Ingredients) {
        this.ingredients = WeakReference(ingredients)
    }

    fun getRecipe(id: String): RatingRecipes.Recipe {
        return ingredients.get()?.let { ingredients ->
            val recipeId = ingredients.recipeId
            val enableInAppRating = ingredients.enableInAppRating
            if (debug) {
                val message = "Recipe: $id Target: $recipeId, enableInAppRating: $enableInAppRating"
                Log.d(tag, message)
                Toast.makeText(
                    ingredients.applicationContext,
                    message,
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (id == recipeId) {
                if (enableInAppRating) {
                    Sentiment(
                        message = ingredients.applicationContext.getString(R.string.rr_sentiment_prompt_message),
                        negativeText = ingredients.applicationContext.getString(R.string.rr_sentiment_prompt_negative_text),
                        positiveText = ingredients.applicationContext.getString(R.string.rr_sentiment_prompt_positive_text)
                    )
                } else {
                    InAppRating
                }
            } else {
                None
            }
        } ?: None
    }

    fun cook(activity: Activity, recipe: RatingRecipes.Recipe) {
        ingredients.get()?.let { ingredients ->
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
        ingredients.log("rr_sentiment_prompt_view")
        MaterialAlertDialogBuilder(activity)
            .setMessage(sentiment.message)
            .setNegativeButton(sentiment.negativeText) { _, _ ->
                ingredients.log("rr_sentiment_prompt_negative")
            }
            .setPositiveButton(sentiment.positiveText) { _, _ ->
                ingredients.log("rr_sentiment_prompt_positive")
            }
            .show()
    }

    private fun showInAppRating(
        activity: Activity,
        ingredients: RatingRecipes.Ingredients
    ) {
        if (debug) {
            val message = "In-App Rating Requested"
            Log.d(tag, message)
            Toast.makeText(ingredients.applicationContext, message, Toast.LENGTH_SHORT)
                .show()
        }

        ingredients.log("rr_in_app_rating_start")
        val manager = ReviewManagerFactory.create(ingredients.applicationContext)
        val request = manager.requestReviewFlow()
        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val reviewInfo = task.result
                val flow = manager.launchReviewFlow(activity, reviewInfo)
                flow.addOnCompleteListener { _ ->
                    ingredients.log("rr_in_app_rating_complete")
                    if (debug) {
                        val message = "In-App Rating Complete"
                        Log.d(tag, message)
                        Toast.makeText(
                            ingredients.applicationContext,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                ingredients.log("rr_in_app_rating_failure")
                if (debug) {
                    val message = "In-App Rating Failure: ${task.exception.toString()}"
                    Log.d(tag, message)
                    Toast.makeText(
                        ingredients.applicationContext,
                        message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}