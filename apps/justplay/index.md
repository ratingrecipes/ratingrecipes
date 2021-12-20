# JustPlay Rating Recipe

## About

<img src='https://play-lh.googleusercontent.com/NsxkIj-ZBu6J08YUCuPxcX8MewQQGiV6EUPdei5zV-VwGmuW3BzjrRg30yi8s0YMmt4=s360-rw' width='10%'>

JustPlay

**Genre** Entertainment

**Links** [Android](https://play.google.com/store/apps/details?id=com.justplay.app)

**Similar Apps** MISTPLAY

**Recipe Type** Event Trigger

**Rating Type** In-App Rating

**Recipe Date** December 2021

## Goal
Show the in-app rating on app launch if the user has cashed out.

## Screenshots
<p align="center">
<img src='https://user-images.githubusercontent.com/140911/146697089-108b3d01-5c95-4703-b440-f258a1876012.jpg' width='45%'>
<img src='https://user-images.githubusercontent.com/140911/146697099-aff40bf0-7777-4b9d-98c7-cb5ff68d8b54.jpg' width='45%'>
</p>

## Rating Flow
![rating-flow-diagram](https://www.plantuml.com/plantuml/proxy?]fmt=svg&src=https://raw.githubusercontent.com/ratingrecipes/ratingrecipes/master/apps/justplay/flow.iuml)

## Configuration

### Device Variables
```
key.lastCashout
key.lastRatingDialogDisplayed

Unused:
DIALOG_RATE_SECONDS_TO_ELAPSE_AFTER_CASHOUT
key.latestRatingDialogResult
```

### More recipes

[Subscribe to receive new recipes each week](https://newsletter.ratingrecipes.com/)

[Recent recipes](https://ratingrecipes.com)
