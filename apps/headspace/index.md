# Headspace Rating Recipe

## About

<img src='https://play-lh.googleusercontent.com/y6ZZo_LKpI8sBXAHb9TMSfgoY96yVU0ecumaOb8hckSdvDzdssiQd2QW1cx1CrjvA4bQ=s360-rw' width='10%'>

Headspace

**Genre** Meditation

**Links** [Apple](https://apps.apple.com/us/app/headspace-meditation-sleep/id493145008)/[Android](https://play.google.com/store/apps/details?id=com.getsomeheadspace.android)

**Similar Apps** [Calm](https://ratingrecipes.com/apps/calm)

**Recipe Type** Duration Trigger

**Rating Type** In-App Rating

**Recipe Date** October 2021

## Goal
Show the in-app rating after a specific duration

## Screenshots
<p align="center">
<img src='https://user-images.githubusercontent.com/140911/138485086-ce409498-e6e6-473d-bcac-1689a3a90de1.png' width='30%'> <img src='https://user-images.githubusercontent.com/140911/138485089-8ece39fc-b9b0-4c36-96e5-b892816e9e8f.png' width='30%'> <img src='https://user-images.githubusercontent.com/140911/138485080-fedabbfd-7a66-4c08-916e-3ca938fbd2c8.png' width='30%'>
</p>

## Rating Flow
![rating-flow-diagram](https://www.plantuml.com/plantuml/proxy?]fmt=svg&src=https://raw.githubusercontent.com/ratingrecipes/ratingrecipes/master/apps/headspace/flow.iuml)

## Configuration

### Analytics
```
content feedback survey
startInAppReview isInAppReviewAvailable = true/false
requestReviewFlow complete
launchReviewFlow complete
```

### Device Variables
```
has rating screen been shown
```

### Backend variables
```
is machine learning feedback loop enabled
is in-app rating enabled
```

### More recipes

[Subscribe to receive new recipes each week](https://newsletter.ratingrecipes.com/)

[Recent recipes](https://ratingrecipes.com)
