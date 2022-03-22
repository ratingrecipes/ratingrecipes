package com.ratingrecipes

import android.app.Activity
import android.content.Context
import androidx.annotation.NonUiContext
import com.ratingrecipes.internal.RatingRecipesImpl

object RatingRecipes {

    fun prepare(ingredients: Ingredients) = ratingRecipes.prepare(ingredients)

    fun getRecipe(id: String): Recipe = ratingRecipes.getRecipe(id)

    fun cook(activity: Activity, recipe: Recipe) = ratingRecipes.cook(activity, recipe)

    var debug: Boolean = false
        set(value) {
            ratingRecipes.debug = value
            field = value
        }

    sealed interface Ingredients {
        val applicationContext: Context

        data class Firebase(
            @NonUiContext override val applicationContext: Context,
            val firebase: com.google.firebase.ktx.Firebase
        ) : Ingredients
    }

    sealed interface Recipe {
        interface None : Recipe
        interface InAppRating : Recipe
        interface Sentiment : Recipe {
            val message: String
            val negativeText: String
            val positiveText: String
        }
    }

    private val ratingRecipes: RatingRecipesImpl by lazy { RatingRecipesImpl() }
}