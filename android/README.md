## Rating Recipes Android SDK

### Description

The Rating Recipes SDK for Android.

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

<img width="791" alt="initial-config" src="https://user-images.githubusercontent.com/140911/161319896-31379c76-3b82-4ba3-9d6f-3c4f45e74a8f.png">

https://user-images.githubusercontent.com/140911/161320810-d4494416-81e1-44c5-b6cd-cfdcd0327405.mov

You can then take advantage of Firebase's [Remote Config Personalization](https://firebase.google.com/docs/remote-config/personalization) feature.
This will automatically identify the best place for different users in your app to rate.

We recommend showing the sentiment prompt to a small percentage of your users.
But the exact value depends on your user base size and how easily users reach the rating locations.
You can set the objective to the positive sentiment button click (`rr_sentiment_prompt_positive`).

<img width="792" alt="sentiment-config" src="https://user-images.githubusercontent.com/140911/161320011-b51d4445-ed55-46fd-9e1b-cc29eb7c4c3c.png">

https://user-images.githubusercontent.com/140911/161320769-c7a2804a-3ab6-4e9d-a6ef-9019bd01d5e7.mov

You can then monitor the Remote Config Personalization results to see which locations are ideal.
Once you feel Personalization has identified the happiest locations for users,
you can now switch off the sentiment prompt and enable in-app rating.

<img width="786" alt="final-config" src="https://user-images.githubusercontent.com/140911/161320003-dcdca2e3-c7e1-4662-9e9a-97e75a612ee2.png">

https://user-images.githubusercontent.com/140911/161320741-ca82e549-59cf-48cb-b02f-5cf9558fbcdd.mov

### Test App
If you want to see the SDK in action, check out the [Android Test App](https://github.com/ratingrecipes/ratingrecipes/blob/main/android/app/src/main/java/com/ratingrecipes/app/MainActivity.kt).

![test-app-sentiment-prompt](https://user-images.githubusercontent.com/140911/161320584-49e75f7d-9f89-416a-8de3-0107dbd4be5b.png)

https://user-images.githubusercontent.com/140911/161320684-6109fa6f-d4f9-4a7f-85b3-f1bb0de07449.mov
