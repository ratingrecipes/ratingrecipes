package com.ratingrecipes

import com.ratingrecipes.internal.RatingRecipesImpl

object RatingRecipes {

    private val ratingRecipes = RatingRecipesImpl()

    fun setDebug(enableDebug: Boolean) {
        ratingRecipes.enableDebug = enableDebug
    }

    fun getRecipe(id: String): Recipe = ratingRecipes.getRecipe(id)

    fun cook(recipe: Recipe) = ratingRecipes.cook(recipe)

    sealed interface Recipe {
        interface None : Recipe
        interface InAppRating : Recipe
        interface Sentiment : Recipe {
            val message: String
            val negativeText: String
            val positiveText: String
        }
    }
}