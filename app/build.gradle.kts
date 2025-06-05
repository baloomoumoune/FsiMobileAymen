plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.fsi"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.fsi"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation(libs.room.compiler)

    // ✅ Retrofit avec exclusion des annotations IntelliJ
    implementation("com.squareup.retrofit2:retrofit:2.9.0") {
        exclude(group = "com.intellij", module = "annotations")
    }
    implementation("com.squareup.retrofit2:converter-gson:2.9.0") {
        exclude(group = "com.intellij", module = "annotations")
    }


    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
configurations.all {
    resolutionStrategy.eachDependency {
        if (requested.group == "com.intellij" && requested.name == "annotations") {
            useTarget("org.jetbrains:annotations:13.0")
            because("Résout le conflit entre annotations-12.0 et annotations-13.0")
        }
    }
}



