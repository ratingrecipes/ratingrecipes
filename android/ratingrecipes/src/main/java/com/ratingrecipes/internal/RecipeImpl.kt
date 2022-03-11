package com.ratingrecipes.internal

import com.ratingrecipes.RatingRecipes

internal object None : RatingRecipes.Recipe.None
internal object InAppRating : RatingRecipes.Recipe.InAppRating
internal data class Sentiment(
    override val message: String,
    override val negativeText: String,
    override val positiveText: String,
) : RatingRecipes.Recipe.Sentiment