# ReignDesign Test

For this project was used some technologies in order to improve and a speed up its development, such as:

-RXjava: for multi-threaded process and requests.

-sqlbrite: adapter for Sqlite with RXjava support.

-retrofit2: type-safe HTTP client.

-mockito: For mock testing.

## System and platform requirements

-Android studio 3 beta-6
-SDK Version 26
-Build tools Version 26.0.1
-Min SDK Version 19
-Target SDK Version 26

## Pattern and Architecture

The architecture implemented was MVP with dependency injection without dagger.

## Flavors

This project has two flavors: mock and prod.
Which depending on what flavor the APK is built with the API call with either connect to a local JSON file (inside /mock/assets/api) to load the articles or connect to https://hn.algolia.com/api/v1/.
