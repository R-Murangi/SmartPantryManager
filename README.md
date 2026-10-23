# Smart Pantry Manager

A beginner-level Android application written in Java for tracking pantry ingredients and suggesting recipes that can be made with the ingredients that are already available.

## Technology
- Java and Android Studio
- XML layouts
- SQLite via `SQLiteOpenHelper`
- RecyclerView with a custom adapter
- Intents for screen navigation

## Features
- Create, read, update and delete pantry ingredients
- Quantity, unit and optional expiry date
- 20 recipes inserted when the database is first created
- Strict recipe matching: every required ingredient and required amount must be available
- Recipe details and preparation methods
- A settings screen that saves a preference locally
- White and pink interface

## Requirements
Install Android Studio and use its Android SDK setup wizard. Open this project folder in Android Studio. Allow Gradle to sync, create an Android Virtual Device (AVD) from Device Manager, and press Run.

## First run
The recipe collection is inserted automatically when SQLite creates the database. Pantry items are empty until you add them. Data remains on the emulator/device between app launches unless app data is cleared or the app is uninstalled.

## Important learning note
Read and test the code before submitting it. Add your own screenshots, write about the actual problems you encounter, create genuine incremental Git commits while developing, and make sure you can explain the database and matching method in your own words.
