# Epic Kid's Books & Reading Rating Recipe

## About

<img src='https://play-lh.googleusercontent.com/v50zN_2wAEaFrfkDbhAIOt63lKGUc78UeTkYeXKPEaw0BuLDOpf4rZwJfIiCi9tpCw=s360-rw' width='10%'>

Epic Kid's Books & Reading

**Genre** Education

**Links** [Apple](https://apps.apple.com/us/app/epic-kids-books-reading/id719219382)/[Android](https://play.google.com/store/apps/details?id=com.getepic.Epic)

**Similar Apps** ABCMouse.com

**Recipe Type** Button Trigger

**Rating Type** Custom Rating Prompt

**Recipe Date** May 2022

## Goal
Show a custom rating prompt when the user finishes, favorites, or rates a book

## Rating Flow
![rating-flow-diagram](https://www.plantuml.com/plantuml/proxy?]fmt=svg&src=https://raw.githubusercontent.com/ratingrecipes/ratingrecipes/master/apps/epic/flow.iuml)

## Configuration

### Analytics
```
review_prompt_choice
review_prompt_shown
```

### Device Variables
```
HAS_BEEN_REVIEWED
LAST_ASK_TIMESTAMP
LAST_VERSION_KEY
FINISH_BOOK_DELAY_COUNT
```

### More recipes

[Subscribe to receive new recipes each week](https://newsletter.ratingrecipes.com/)

[Recent recipes](https://ratingrecipes.com)
