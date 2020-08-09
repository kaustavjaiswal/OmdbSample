object Releases {
    const val versionCode = 1
    const val versionName = "1.0"
}

object DefaultConfig {
    const val minSdk = 21
    const val targetSdk = 30
    const val compileSdk = 30
}

object AndroidConfiguration {
    const val buildToolsVersion = "30.0.0"
}

object Versions {
    const val androidArchRoomVersion = "2.3.0-alpha02"
    const val androidKtxVersion = "1.2.0"
    const val activityKtxVersion = "1.1.0"
    const val cardViewVersion = "1.0.0"
    const val constraintLayoutVersion = "1.1.3"
    const val coroutinesVersion = "1.3.5"
    const val daggerHiltVersion = "2.28-alpha"
    const val pagingAlphaVersion = "3.0.0-alpha02"
    const val daggerHiltCompilerVersion = "1.0.0-alpha01"
    const val kotlinVersion = "1.3.72"
    const val materialDesignVersion = "1.2.0-alpha02"
    const val recyclerViewVersion = "1.1.0"
    const val supportLibrary = "1.2.0-beta01"
    const val timberVersion = "4.7.1"
    const val jUnitVersion = "4.13"
    const val mockitoCoreVersion = "3.3.3"
    const val mockitoAndroidVersion = "3.3.3"
    const val okHttpVersion = "4.5.0"
    const val moshiVersion = "1.9.2"
    const val lifecycleVersion = "2.2.0"
    const val lifecycleTestingVersion = "2.1.0"
    const val retrofitVersion = "2.7.1"
}

object Dependencies {

    // Support Libraries
    const val appcompat = "androidx.appcompat:appcompat:${Versions.supportLibrary}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayoutVersion}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerViewVersion}"
    const val material = "com.google.android.material:material:${Versions.materialDesignVersion}"
    const val cardview = "androidx.cardview:cardview:${Versions.cardViewVersion}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.androidKtxVersion}"

    // Dagger
    const val daggerCompiler =
        "com.google.dagger:hilt-android-compiler:${Versions.daggerHiltVersion}"
    const val hiltCompiler = "androidx.hilt:hilt-compiler:${Versions.daggerHiltCompilerVersion}"
    const val hiltViewModel =
        "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.daggerHiltCompilerVersion}"
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.daggerHiltVersion}"

    // Room
    const val room = "androidx.room:room-runtime:${Versions.androidArchRoomVersion}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.androidArchRoomVersion}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.androidArchRoomVersion}"

    // Paging
    const val paging = "androidx.paging:paging-runtime:${Versions.pagingAlphaVersion}"

    // Lifecycle
    const val lifecycleLiveData =
        "androidx.lifecycle:lifecycle-livedata:${Versions.lifecycleVersion}"
    const val lifecycleCompiler =
        "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycleVersion}"
    const val lifecycleViewModel =
        "androidx.lifecycle:lifecycle-viewmodel:${Versions.lifecycleVersion}"
    const val lifecycleLiveDataKtx =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleVersion}"
    const val lifecycleViewModelKtx =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleVersion}"
    const val lifecycleTesting =
        "androidx.arch.core:core-testing:${Versions.lifecycleTestingVersion}"

    // Networking

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okHttpVersion}"
    const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshiVersion}"
    const val okhttpLoggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okHttpVersion}"
    const val moshiKotlinCodegen =
        "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshiVersion}"
    const val converterMoshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofitVersion}"

    // Testing

    const val junit = "junit:junit:${Versions.jUnitVersion}"
    const val mockitoCore = "org.mockito:mockito-core:${Versions.mockitoCoreVersion}"
    const val mockitoAndroid = "org.mockito:mockito-android:${Versions.mockitoAndroidVersion}"

    // Kotlin
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlinVersion}"
    const val coroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesVersion}"
    const val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesVersion}"

    // Logging
    const val timber = "com.jakewharton.timber:timber:${Versions.timberVersion}"

    // Activity Ktx
    const val activityKtx = "androidx.activity:activity-ktx:${Versions.activityKtxVersion}"

    // Glide
    const val glide = "com.github.bumptech.glide:glide:4.11.0"
    const val glideCompiler = "com.github.bumptech.glide:compiler:4.11.0"

    // Lottie
    const val lottie = "com.airbnb.android:lottie:3.4.1"

    // Testing
    const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0"
    const val mockWebServer = "com.squareup.okhttp3:mockwebserver:4.8.0"
    const val archCoreTesting = "androidx.arch.core:core-testing:2.1.0"
    const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.8"
    const val hiltTest = "com.google.dagger:hilt-android-testing:2.28-alpha"
    const val espressso = "androidx.test.espresso:espresso-core:3.2.0"
    const val testCoreKtx = "androidx.test:core-ktx:1.3.0-rc01"
    const val testJunitKtx = "androidx.test.ext:junit-ktx:1.1.2-rc01"
}