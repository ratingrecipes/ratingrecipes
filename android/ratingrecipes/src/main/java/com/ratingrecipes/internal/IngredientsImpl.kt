package com.ratingrecipes.internal

import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.ratingrecipes.RatingRecipes

internal object IngredientsImpl {

    val RatingRecipes.Ingredients.recipeId: String
        get() = getString("rr_recipe_id")

    val RatingRecipes.Ingredients.enableInAppRating: Boolean
        get() = this.getBoolean("rr_enable_in_app_rating")

    fun RatingRecipes.Ingredients.logEvent(eventName: String) {
        when (this) {
            is RatingRecipes.Ingredients.Firebase -> this.firebase.analytics.logEvent(
                eventName,
                null
            )
        }
    }

    private fun RatingRecipes.Ingredients.getString(key: String): String {
        return when (this) {
            is RatingRecipes.Ingredients.Firebase -> this.firebase.remoteConfig.getString(key)
        }
    }

    private fun RatingRecipes.Ingredients.getBoolean(key: String): Boolean {
        return when (this) {
            is RatingRecipes.Ingredients.Firebase -> this.firebase.remoteConfig.getBoolean(key)
        }
    }
}