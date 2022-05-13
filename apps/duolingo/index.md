# Duolingo Rating Recipe

## About

<img src='https://play-lh.googleusercontent.com/hSyebBlYwtE2aMjzSIHasUO9cQv9HgNAw9owy6ADO0szOKYO3rDk60r7jcyXu82Fbq1M=s360-rw' width='10%'>

Duolingo

**Genre** Education

**Links** [Apple](https://apps.apple.com/us/app/duolingo-language-lessons/id570060128)/[Android](https://play.google.com/store/apps/details?id=com.duolingo)

**Similar Apps** Babbel

**Recipe Type** Duration Trigger

**Rating Type** Custom Rating Prompt

**Recipe Date** May 2022

## Goal
Show a custom rating prompt when the app receives a messge from backend and specific duration criteria is met

## Rating Flow
![rating-flow-diagram](https://www.plantuml.com/plantuml/proxy?]fmt=svg&src=https://raw.githubusercontent.com/ratingrecipes/ratingrecipes/master/apps/duolingo/flow.iuml)

## Configuration

### Backend Events
```
anyNpsRating
```

### Analytics
```
RATING_DIALOG_SHOW
RATING_DIALOG_NEUTRAL
RATING_DIALOG_NEGATIVE
RATING_DIALOG_POSITIVE
```

### Device Variables
```
PREF_DONT_SHOW_AGAIN
PREF_TIME_OF_ABSOLUTE_FIRST_LAUNCH
PREF_LAUNCHES_SINCE_LAST_PROMPT
PREF_TIME_OF_LAST_PROMPT
```

### More recipes

[Subscribe to receive new recipes each week](https://newsletter.ratingrecipes.com/)

[Recent recipes](https://ratingrecipes.com)
