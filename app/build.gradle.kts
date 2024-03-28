plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.watersavior"
    compileSdk = rootProject.extra["compileSdk"] as Int

    defaultConfig {
        applicationId = "com.example.watersavior"
        minSdk = rootProject.extra["minSdk"] as Int
        targetSdk = rootProject.extra["targetSdk"] as Int
        versionCode = rootProject.extra["versionCode"] as Int
        versionName = rootProject.extra["versionName"] as String

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
//        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:${rootProject.extra["core-ktxVersion"]}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.0")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation ("com.google.android.material:material:1.4.0")   // material 추가
    implementation("androidx.navigation:navigation-compose:2.7.6")
    implementation("com.google.android.gms:play-services-analytics:18.0.4")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    // hilt
    implementation ("com.google.dagger:hilt-android:2.50")
    ksp ("com.google.dagger:hilt-compiler:2.50")   // Hilt compiler

    // vico
    implementation("com.patrykandpatrick.vico:compose:${rootProject.extra["vico-version"]}") // For Jetpack Compose.
    implementation("com.patrykandpatrick.vico:compose-m3:${rootProject.extra["vico-version"]}") // For `compose`. Creates a `ChartStyle` based on an M3 Material Theme.

    // compose에서 hilt
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")

    // okHttp, requestBodu 사용하기 위함
    implementation ("com.squareup.okhttp3:okhttp:4.9.1")

    // retrofit
    implementation ("com.google.code.gson:gson:2.8.9")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.6.0")

    // okhttp
    implementation ("com.squareup.okhttp3:okhttp:4.8.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.8.0")

    // material icon
    implementation ("androidx.compose.material:material-icons-extended:${rootProject.extra["compose_ui_version"]}")

    // The compose calendar library
    implementation ("com.kizitonwose.calendar:compose:2.4.1")

//    implementation  ("com.android.tools:desugar_jdk_libs:2.0.4")

    // viewPager2
    implementation ("com.google.accompanist:accompanist-pager:0.20.1")
    implementation ("com.google.accompanist:accompanist-pager-indicators:0.20.1")

    // viewPager2
    implementation ("com.tbuonomo:dotsindicator:5.0")
    implementation ("androidx.compose.foundation:foundation:1.4.0")
}