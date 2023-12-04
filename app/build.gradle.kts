plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
  id("com.google.dagger.hilt.android")
  id ("kotlin-kapt")
  id ("kotlin-parcelize")
}

android {
  namespace = "com.technocraze.newsappassesment"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.technocraze.newsappassesment"
    minSdk = 24
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


    buildConfigField("String", "BASE_API_URL", "\"https://newsapi.org/\"")
    buildConfigField("String", "API_KEY", "\"337c3f99b4da48c98763671b9f41963b\"");
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
  }
  buildFeatures{
    viewBinding = true
    dataBinding = true
    buildConfig =  true
  }

}

dependencies {

  implementation("androidx.core:core-ktx:1.9.0")
  implementation("androidx.appcompat:appcompat:1.6.1")
  implementation("com.google.android.material:material:1.10.0")
  implementation("androidx.constraintlayout:constraintlayout:2.1.4")
  testImplementation("junit:junit:4.13.2")
  androidTestImplementation("androidx.test.ext:junit:1.1.5")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


  //retrofit
  implementation ("com.squareup.retrofit2:retrofit:2.9.0")
  implementation ("com.squareup.okhttp3:okhttp:4.7.2")
  implementation ("com.squareup.okhttp3:logging-interceptor:4.7.2")
  implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
  implementation ("com.google.code.gson:gson:2.10.1")


  // coroutine
  val coroutine = "1.6.4"
  implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutine")
  implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutine")
  testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutine")

  //viewmodel
  implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")

  //hilt
  implementation("com.google.dagger:hilt-android:2.47")
  implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
  implementation ("androidx.core:core-ktx:1.12.0")
  kapt("com.google.dagger:hilt-android-compiler:2.47")
  kapt("androidx.hilt:hilt-compiler:1.1.0")

  //glide
  implementation ("com.github.bumptech.glide:glide:4.12.0")
  kapt ("com.github.bumptech.glide:compiler:4.12.0")

  testImplementation ("com.squareup.okhttp3:mockwebserver:4.9.0")

  testImplementation ("org.mockito:mockito-core:3.6.0")
  testImplementation ("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")

  //fragment
  val fragment_version = "1.6.2"
  implementation ("androidx.fragment:fragment-ktx:$fragment_version")

  //paging version
  val paging_version = "3.2.1"
  implementation ("androidx.paging:paging-runtime:$paging_version")

  // rxjava
  implementation ("io.reactivex.rxjava3:rxandroid:3.0.2")

  implementation ("io.reactivex.rxjava3:rxjava:3.1.5")

}