# NewsBreak Rating Recipe

## About

<img src='https://play-lh.googleusercontent.com/giKCCPigafUbKQ1AkXVxSjQ1PggetEI96ORNKxxhQdvGAFhto71kO4zf7gZ9oOdLIQe5=s180-rw' width='10%'>

NewsBreak

**Genre** News

**Links** [Apple]([https://apps.apple.com/us/app/etsy-custom-creative-goods/id477128284)/[Android](https://play.google.com/store/apps/details?id=com.particlenews.newsbreak)

**Similar Apps** Smartnews

**Recipe Type** Backend Trigger

**Rating Type** In-App Rating

**Recipe Date** December 2021

## Goal
Rating triggered by the backend.

Show the in-app rating if the user has a high sentiment rating.

## Rating Flow
![rating-flow-diagram](https://www.plantuml.com/plantuml/proxy?]fmt=svg&src=https://raw.githubusercontent.com/ratingrecipes/ratingrecipes/master/apps/newsbreak/flow.iuml)

## Configuration

### Analytics
```
dismissed_feedback_view
ask_for_rating_displayed
app_store_rating_suggested
rating_submitted
feedback_text_submitted
fb_rating_v3
fb_rating_v3_last
fb_rating_v3_first
```

### Backend Variables
```
android_app_rating_feedback
```

### More recipes

[Subscribe to receive new recipes each week](https://newsletter.ratingrecipes.com/)

[Recent recipes](https://ratingrecipes.com)
