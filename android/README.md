## Rating Recipes Android SDK

### Description

The official Rating Recipes SDK for Android.

[RatingRecipes.com](https://ratingrecipes.com/) teaches you how the top apps ask for ratings.

This SDK allows you to optimize your rating quickly and without lots of code.

For a 60 second demo of the SDK, [check out this Youtube video](https://youtu.be/AGSGmG1EZ-U).

### Prepare
Initialize the SDK. The current SDK uses Firebase ([setup Firebase](https://firebase.google.com/docs/android/setup) if you haven't),
but more experimentation platforms are in the works.

    import com.google.firebase.ktx.Firebase

    class MainApplication : Application() {

        override fun onCreate() {
            super.onCreate()

            RatingRecipes.debug = BuildConfig.DEBUG
            RatingRecipes.prepare(
                RatingRecipes.Ingredients.Firebase(
                    applicationContext = applicationContext,
                    firebase = Firebase
                )
            )
        }
    }

### Fetch the Recipe and Cook
Request recipes at places where you think your users are happiest.
For example, after they complete a level in a game.

Cook the recipe to either show a sentiment prompt or an in-app rating.
Rating Recipes intelligently tries to pick the best recipe for the user.

    class GameLevel : AppCompatActivity() {
    
        fun beatLevelOne() {
            val recipe = RatingRecipes.getRecipe(id = "level_1_complete")
            RatingRecipes.cook(this, recipe)
        }
    
        fun beatLevelTwo() {
            val recipe = RatingRecipes.getRecipe(id = "level_2_complete")
            RatingRecipes.cook(this, recipe)
        }
    }

### Configure Firebase
You will need to configure Firebase Remote Config with two values:
`rr_recipe_id` - String - default value empty string - identify locations in the app that use the SDK
`rr_enable_in_app_rating` - Boolean - default value false -  false shows sentiment prompt, true shows in-app rating

PICTURE AND VIDEO OF DEFAULTS

You can then take advantage of Firebase's [Remote Config Personalization](https://firebase.google.com/docs/remote-config/personalization) feature.
This will automatically identify the best place for different users in your app to rate.

We recommend showing the sentiment prompt to a small percentage of your users.
But the exact value depends on your user base size and how easily users reach the rating locations.
You can set the objective to the positive sentiment button click (`rr_sentiment_prompt_positive`).

PICTURE AND VIDEO OF SETUP

You can then monitor the Remote Config Personalization results to see which locations are ideal.
Once you feel Personalization has identified the happiest locations for users,
you can now switch off the sentiment prompt and enable in-app rating.

PICTURE AND VIDEO OF SETUP

### Test App
If you want to see the SDK in action, check out the [Android Test App](https://github.com/ratingrecipes/ratingrecipes/blob/main/android/app/src/main/java/com/ratingrecipes/app/MainActivity.kt).

PICTURE AND VIDEO OF TEST APP