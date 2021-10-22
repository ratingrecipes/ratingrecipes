# Calm Rating Recipe

[Subscribe to Receive New Recipes](https://www.getrevue.co/profile/ratingrecipes)

[Recent Recipes](https://ratingrecipes.com)

## About
**Name** Calm

**Genre** Meditation

**Icon** 

<img src='https://play-lh.googleusercontent.com/S76GphUu2pZa249td2Bb4XAhLcPRrFdL1zp_5qU1ouukvRq9r0-8jJ-CruaTtdT6g84=s180-rw' width='10%'>

**Links** [Apple](https://apps.apple.com/us/app/calm-sleep-meditation/id571800810)/[Android](https://play.google.com/store/apps/details?id=com.calm.android)

**Similar Apps** Headspace

**Recipe Type** Button Trigger

**Rating Type** In-App Rating, Custom Prompt

**Recipe Date** October 2021

## Goal
When a user favorites an item, show the in-app rating

## Screenshots
<p align="center">
<img src='https://user-images.githubusercontent.com/140911/137760933-ec83ffae-2f90-42eb-9724-95ed328a51c4.png' width='20%'> <img src='https://user-images.githubusercontent.com/140911/137760967-36059afc-452f-4ad2-8236-ed73d69729a7.png' width='20%'> <img src='https://user-images.githubusercontent.com/140911/137760962-8caefbbe-9f32-45c9-8f19-a773da389eca.png' width='20%'> <img src='https://user-images.githubusercontent.com/140911/137760970-28a04cf1-1999-4235-acb2-371b69fbf30d.jpeg' width='20%'>
</p>

## Rating Flow
![rating-flow-diagram](https://www.plantuml.com/plantuml/proxy?]fmt=svg&src=https://raw.githubusercontent.com/ratingrecipes/ratingrecipes/master/apps/calm/flow.iuml)

## Configuration

### Analytics
```
Rating Dialog : In-App Rating Flow : Started

Rating Dialog : Love : Shown
Rating Dialog : Love : Yes : Tapped
Rating Dialog : Love : No : Tapped

Rating Dialog : Rating : Shown
Rating Dialog : Rate : Rate Calm : Tapped
Rating Dialog : Rate : No thanks : Tapped
Rating Dialog : Rate : Maybe Later : Tapped

Rating Dialog : Feedback : Shown
Rating Dialog : Feedback : Yes : Tapped
Rating Dialog : Feedback : Dismiss : Tapped
```

### Device Variables
```
is rating screen displayed
# of rating screens displayed
has rated current version
```

### Backend variables
```
is in-app rating enabled
```
