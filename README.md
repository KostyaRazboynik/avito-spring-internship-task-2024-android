# Kinopoisk Quick Search

[Technical Specification (ru)](https://github.com/avito-tech/android-trainee-assignment-2024?tab=readme-ov-file)

## Installation
Clone this repository
```gradle
git clone https://github.com/KostyaRazboynik/avito-spring-internship-task-2024-android
```
To launch the application, you'll need the Kinopoisk API token ([documentation on obtaining the token and using the API](https://api.kinopoisk.dev/documentation))

After receiving the token, you can
- change the value of KINOPOISK_API_KEY in the [secrets.default.properties](https://github.com/KostyaRazboynik/avito-spring-internship-task-2024-android/blob/master/secrets.default.properties#L1) file
- create a secrets.properties file locally, add
```kotlin
KINOPOISK_API_KEY="<YOUR API KEY>"
KINOPOISK_BASE_URL="https://api.kinopoisk.dev/"
```
(secrets.properties is already in [.gitignore](https://github.com/KostyaRazboynik/avito-spring-internship-task-2024-android/blob/master/.gitignore#L4), so don't worry about your token)

## Build Variants
You can choose debug build or release build, depending on whether you want to see the debug logs from [Logger](https://github.com/KostyaRazboynik/avito-spring-internship-task-2024-android/blob/master/core/utils/src/main/kotlin/com/kostyarazboynik/utils/Logger.kt) and [Logging Interceptor](https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor)

## Technologies used

- [Retrofit](https://github.com/square/retrofit) + [OkHttp3](https://github.com/square/okhttp/tree/master) + [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization)
- [Dagger2](https://github.com/google/dagger)
- [Room](https://developer.android.com/training/data-storage/room) + [Gson](https://github.com/google/gson)
- [Kotlin Coroutines + Flow](https://github.com/Kotlin/kotlinx.coroutines)
- [Coil](https://coil-kt.github.io/coil/)
- [Detekt](https://detekt.dev/)
- XML
- MVVM, Clean Architecture

### Problems
[List of problems and their solution](https://github.com/KostyaRazboynik/avito-spring-internship-task-2024-android/blob/master/PROBLEMS_RU.md) (ru)

