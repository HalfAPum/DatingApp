import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id ("com.google.gms.google-services")
    id ("com.google.firebase.crashlytics")
    id("com.google.devtools.ksp") version "1.7.0-1.0.6"
}
android {
    namespace = "com.narvatov.datingapp"
    compileSdkVersion(33)

    defaultConfig {
        applicationId = "com.narvatov.datingapp"
        minSdkVersion(24)
        targetSdkVersion(33)
        versionCode = 1
        versionName = "1.0"
    }

    kotlin {
        sourceSets.all {
            kotlin.srcDir("build/generated/ksp/$name/kotlin")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0"
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.freeCompilerArgs = listOf("-Xcontext-receivers")
}

dependencies {
    implementation("com.google.devtools.ksp:symbol-processing-api:1.7.0-1.0.6")

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.compose.ui:ui:1.2.0")
    implementation("androidx.compose.material:material:1.2.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.2.0")
    implementation("androidx.compose.material:material-icons-extended:1.2.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("androidx.activity:activity-compose:1.5.1")
    implementation("androidx.navigation:navigation-compose:2.5.2")

    // Koin for Android
    implementation("io.insert-koin:koin-android:3.2.0")
    implementation("io.insert-koin:koin-androidx-compose:3.2.0")
    implementation("io.insert-koin:koin-annotations:1.0.2")
    ksp ("io.insert-koin:koin-ksp-compiler:1.0.2")
    implementation("io.insert-koin:koin-androidx-workmanager:3.1.2")

    //Timber
    implementation("com.jakewharton.timber:timber:5.0.1")



    //Common extensions
    implementation("com.github.HalfAPum:CommonExtensions:0.2.1")

    //Coil
    implementation("io.coil-kt:coil-compose:2.2.2")

    //Date picker
    implementation("com.github.DogusTeknoloji:compose-date-picker:1.0.1")

    //Data store
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    //Firebase
    implementation (platform("com.google.firebase:firebase-bom:31.1.1"))
    implementation ("com.google.firebase:firebase-crashlytics-ktx")
    implementation ("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-messaging-ktx:23.1.0")
    implementation("com.google.firebase:firebase-firestore-ktx:24.4.1")

    //Test dependencies

    // Koin Test
    testImplementation("io.insert-koin:koin-test:3.2.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.2.0")

    //Debug
    debugImplementation("androidx.compose.ui:ui-tooling:1.2.0")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.2.0")
}