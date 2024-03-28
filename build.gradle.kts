// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
//    ext {
//        val compose_ui_version = "1.3.0-alpha01"
//        val hilt_version = "2.42"
//        val material3_version = "1.0.1"
//    }

    extra.apply {
        set("compileSdk", 34)
        set("targetSdk", 34)
        set("minSdk", 24)
        set("versionName", "1.0")
        set("versionCode", 1)
        set("core-ktxVersion", "1.10.1")
        set("hilt-version", "2.50")
        set("compose_ui_version", "1.3.0-alpha01")
        set("vico-version", "2.0.0-alpha.6")
    }
}

plugins {
    id("com.android.application") version "8.2.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.12" apply false
    id ("com.google.dagger.hilt.android") version "2.50" apply false
}
