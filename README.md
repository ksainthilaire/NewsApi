# Instant System - NewsAPI

# Table of Contents
1. [Application Architecture](#application-architecture)
2. [Third-party libraries](#third-party-libraries)
4. [Configuration file](#news-api)
5. [Negative aspects of the project / Ways for improvement](#negative-aspects-of-the-project--ways-for-improvement)
6. [Thanks](#thanks)

---

## Application Architecture
I chose to use Clean Architecture to split the app into independent layers (see Part I).
Each layer has a clear role, which makes the code easier to read, understand, and maintain.
For the presentation layer, I decided to use the MVVM pattern because it separates responsibilities clearly:
- View (Compose UI): only shows the data and sends user actions (clicks, typing, etc.).
- ViewModel: keeps the UI state (StateFlow, Flow) and contains the presentation logic.
- Model: represents the data used in the app.
This works very well with Jetpack Compose, because of the link between observable state and lifecycle.
Compared to other patterns (MVI, MVP):
- Less boilerplate code than MVI and MVP.
- Easier to learn and maintain.
- Faster to develop.

# Figma

I made a small draft of the design with Material 3 components.  
This draft is separate from the next versions that I will develop.

👉 [See the Figma design here](https://www.figma.com/design/HCwn26xv4Gtu2JM09O2H5P/newsapi-ksainthilaire-sample?node-id=0-1&t=29dJZNMGl9TJ6Zrx-1)

### Modules

- **data**  
  Contains repositories, error handling, API definitions with Retrofit, request and response templates, and local persistence with Room.
    - **repository**  
    Contains the main repository implementation (`NewsRepositoryImpl.kt`), which orchestrates data retrieval and persistence.

    - **mapper**  
      Provides mapping functions between remote models (`Dto`), local entities (`Entity`), and domain objects.
      - `ArticleMappers.kt`: article mapping.
      - `ArticleSourceMappers.kt`: source mapping.

    - **local**  
      Handles local persistence using **Room**.
      - **entity**: local entities (`ArticleEntity`, `ArticleSourceEntity`).
      - **dao**: data access objects (`ArticleDao`, `ArticleSourceDao`).
      - `AppDatabase.kt`: Room database definition.

    - **remote**  
      Handles remote API integration using **Retrofit**.
      - **dto**: Data Transfer Objects (`ArticleDto`, `ArticleSourceDto`, etc.).
      - `NewsService.kt`: Retrofit interface defining API endpoints.


- **app**  
  Contains the main layer of the application.
  - **presentation**: Contains the user interface built with Jetpack Compose.
    - **presentation/components**: Reusable layouts and scaffolds.
    - **presentation/theme**: The design system including colors, typography and theme setup.
    - **presentation/feature**: The different UI features of the app.
      - **feature/home**: Displays the list of news articles with the title, the source and an image.
      - **feature/article**: Displays the detail of a news article with the title, the content, the source, the author and an image.
      - **feature/saved**: Displays the list of saved articles with the title, the source and an image.

- **domain**  
  Contains the core business logic of the application.
  - **domain/model**: Defines the data models such as Article and Source.
  - **domain/repository**: Defines the repository contracts for fetching and saving articles.
  - **domain/usecase**: Contains the use cases that represent the actions of the application such as getting headlines, searching news, saving an article or removing an article.

### Design pattern: MVVM (Model View View-Model)

- **features/home**
  - `HomeScreen.kt`: The user interface built with Jetpack Compose.
  - `HomeViewModel.kt`: The class that connects the user interface with the domain use cases.
  - `HomeUiState.kt`: The state of the user interface.

The data flows from the user interface to the ViewModel, then to the use cases, then to the repositories, and finally to the data layer.

---

## Third-party libraries

The libraries and their versions can be configured directly in the `local.properties` file:

```kt
room-runtime = { module = "androidx.room:room-runtime",  version.ref = "room"}
room-ktx = { module = "androidx.room:room-ktx", version.ref = "room"}
room-compiler = { module = "androidx.room:room-compiler", version.ref = "room"}
```

---

## News API

The API key and the base URL can be configured in `local.properties`:

```properties
NEWS_API_URL=https://newsapi.org/
NEWS_API_KEY=ac4a2b10c51e4b27bd0746a3763fde34
```

---

## Negative aspects of the project / Ways for improvement
- Improve how the project is divided into modules
- Add a loading animation with Lottie
- Add more unit tests, end-to-end tests, and integration tests (only 1 for now)
- Remove code and files that are not needed
- Check for memory leaks with LeakCanary
- Give better names to the files
- Protect the app with ProGuard (obfuscation)
- Have a separate module for each feature, and separate dependency injection by module

---

## Thanks

Here are the articles that inspired and helped me for this project:
- [Guide to Android App Modularization](https://developer.android.com/topic/modularization?hl=en)
- [Modularization of Android Applications based on Clean Architecture](https://ahmad-efati.medium.com/modularization-of-android-applications-based-on-clean-architecture-18dc643e0562)
- [Android Modularization using Clean Architecture and other Components](https://princessdharmy.medium.com/android-modularisation-using-clean-architecture-and-other-components-9ee44b061e9f)
- [Jetpack Compose Modular Clean Architecture with Rorty App](https://developersancho.medium.com/jetpack-compose-modular-clean-architecture-with-rorty-app-58d801571ab9)
- [Sharing components between two modules](https://stackoverflow.com/questions/34807554/sharing-components-between-modules)  
