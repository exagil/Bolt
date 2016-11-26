Bolt - Fitness Tracker
======================

Ready to get in shape? Get started to with the Bolt Running and Fitness App and let us be your go­to fitness tracker (distance, time, speed & more) ­ for activities such as running, jogging, biking & walking using GPS.

Screenshots (Nexus 6p)
----------------------

![Run Not Stated](http://res.cloudinary.com/chi6rag/image/upload/c_scale,w_240/v1480132872/nexus6p_run_not_started_p2y6dz.png)
![Run In Progress](http://res.cloudinary.com/chi6rag/image/upload/c_scale,w_280/v1480133119/bolt/nexus6p_run_in_progress.png)
![Run Complete](http://res.cloudinary.com/chi6rag/image/upload/c_scale,w_240/v1480132447/bolt/nexus6p_run_complete.png)

Google Maps Setup
------------------
- Obtain a Google Maps API Key from [here](https://developers.google.com/maps/documentation/android-api/signup#use-key).
- Place the Google Maps API Key in `app/src/main/res/values/strings.xml` with string key as `google_maps_api_key`

Firebase Setup
--------------
- Create a new [Firebase Project](https://console.firebase.google.com/) with Package Name as `com.chiragaggarwal.bolt`
- Place the `google-services.json` file obtained after creation of project in the root directory of the `app module`

Build Instructions (Mac OS X)
-----------------------------

- Clone the repository
- Install [Java](https://java.com/en/download/help/download_options.xml)
- Install [Android Studio](http://developer.android.com/sdk/index.html) with Android SDK Tools
- Setup Google Maps and Firebase keys
- Install [Genymotion](https://www.genymotion.com/)
- Start Genymotion Emulator
- Build the app using `./gradlew clean build`
- In order to install the debug version of the app, run `./gradlew installDebug`
- In order to install the release version of the app, run `./gradlew installRelease`
