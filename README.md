![](docs/Portrait.png)

# Omdb Movie/TV Show Search 🍿

[![GitHub license](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

**Omdb Movie/TV Show Search** is a show search Android application which uses the Omdb API.

## About

Omdb Search is built to showcase the latest in modern android development.

## Built With 🛠
- [Kotlin](https://kotlinlang.org/)
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) - EXPERIMENTAL
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture)
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
  - [ViewBinding](https://developer.android.com/topic/libraries/view-binding)
  - [Paging](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) - ALPHA
- [Dependency Injection](https://developer.android.com/training/dependency-injection)
  - [Hilt-Dagger](https://dagger.dev/hilt/) - ALPHA
  - [Hilt-ViewModel](https://developer.android.com/training/dependency-injection/hilt-jetpack) - ALPHA
- [Retrofit](https://square.github.io/retrofit/)
- [Moshi](https://github.com/square/moshi)
- [Moshi Converter](https://github.com/square/retrofit/tree/master/retrofit-converters/moshi)
- [Material Components for Android](https://github.com/material-components/material-components-android)
- [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html)

## Architecture

**MVVM**

## Issues
* There is a lack of information from Google on how Paging 3 can be tested thoroughly,
probably still being worked on, as the library is still in alpha, hence this sample sticks to basic paging test cases.

## Cases handled

![](docs/Landscape.png)

* Network connectivity change is detected, app displays disconnected toast.
* Page state and loaded images are still retained, even in offline mode.
* Kill and restart app, loads cache data first and attempts network connection in the background.
* Fresh search is made automatically once network is established again.
* All data retrieved is retained in DB and app can be used completely in offline mode as well, showing cached data.

## License
```
MIT License

Copyright (c) 2020 Kaustav Jaiswal

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```