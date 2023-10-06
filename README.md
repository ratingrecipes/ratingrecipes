## Rating Recipes

### Description

[https://ratingrecipes.github.io/ratingrecipes/](https://ratingrecipes.github.io/ratingrecipes/)

This is the community repository for the analysis of rating logic within apps.

The goal of this space is to educate app teams on the most popular strategies to request ratings and reviews.

### Which apps can I analyze?

* While analysis of iOS apps is possible, we focus on Android apps since they are easier to analyze.
* Apps that use the native [Google Play In-app Reviews API](https://developer.android.com/guide/playcore/in-app-review) are the easiest to analyze. Not all apps use the native rating prompt though.
* Apps that use cross-platform technologies like React Native are harder to analyze.
* Apps that are highly obfuscated take much longer to analyze (popular apps tend to be more obfuscated).

### What do I need to analyze apps?

* A strong working knowledge of Java and Android development is very helpful.
* A machine with a high amount of RAM memory is also very useful.

### How do I set up my machine to analyze apps?

We [created a video](https://www.youtube.com/watch?v=Egxv9xWN988) detailing this process step-by-step in case you wish to analyze an app.

Abbreviated instructions below on how to configure your environment to analyze apps:

1. Clone and build [Jadx](https://github.com/skylot/jadx) `git clone https://github.com/skylot/jadx.git && cd jadx && ./gradlew dist`
1. Launch jadx-gui `./build/jadx/bin/jadx-gui`
1. Optional: modify Jadx Preferences -> Decompilation -> Excluded packages `android.accounts android.support android.arch androidx appnav dagger fr io javax kotlin bolts net okhttp3 okio org retrofit2 lib.android.paypal siftscience.android kotlinx com.airbnb com.adjust.sdk com.amplitude.api com.android.installreferrer com.braintreepayments.api com.bumptech.glide com.facebook.shimmer com.getbouncer com.github com.ibm.icu com.iterable.iterableapi com.newrelic com.nimbusds com.salesforce.android com.segment.analytics.android com.sendbird com.sift.api com.squareup com.stripe.android com.uber.rxdogtag com.ults.listeners com.verygoodsecurity.vgscollect com.withpersona.sdk com.google`
1. Download the APK of the app you'd like to analyze (e.g., google search "[App Name] APK") and open in jadx-gui. Note: if the file is .xapk, rename to .zip and open to find the actual .apk.
1. Search for `ReviewInfo` within jadx to find the native in-app rating prompt.
1. Alternatively, search for `market://details?id=` and `http://play.google.com/store/apps/details` to find where the app may launch the store listing.
1. Have fun exploring the app!

### Can I analyze more than rating strategies?

Yes, this approach works well for analyzing analytics, in-app subscriptions, and really any feature you can think of.

However, we focus on ratings in this community repository.
