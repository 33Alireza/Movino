# Movino 🎬

**Movino** is a modern, responsive Android application for browsing, searching, and discovering
movies. Built using **100% Jetpack Compose**, **MVVM Architecture**, **Kotlin Coroutines & Flow**, *
*Dagger Hilt**, and **Ktor Client**, Movino demonstrates Modern Android Development (MAD) best
practices and type-safe navigation.

---

## ✨ Features

- **🏠 Home Screen**:
    - Filter movies dynamically by genres using an interactive genre chip slider.
    - Interactive featured movie slider with smooth page indicators.
    - Detailed movie cards showing poster preview, title, IMDB rating, release year, country, and
      duration.
    - State management handling Loading, Error with actionable SnackBar retries, and Success states.

- **🔍 Search Screen**:
    - Real-time search functionality querying movies by title or keywords.
    - Clear search bar input with instant search state feedback.
    - Grid view layout for search results with error and empty state handling.

- **🍿 Movie Detail Screen**:
    - Full movie poster header banner with back navigation.
    - Comprehensive movie metadata including title, IMDB rating, release year, runtime, country, and
      genre tags.
    - Plot summary overview, director details, and full cast/actors list.
    - Interactive movie screenshot gallery slider.

- **🎨 Modern Dark Theme**:
    - Material 3 theme tailored with a custom movie-focused dark palette, clean typography, and
      smooth shapes.

---

## 🛠 Tech Stack & Architecture

Movino is built with **MVVM (Model-View-ViewModel)** architecture and **Unidirectional Data Flow (
UDF)** principles to ensure high separation of concerns, scalability, and testability.

### 🏛️ MVVM Architecture Breakdown

```
┌─────────────────────────────────────────────────────────────────┐
│                          VIEW (Compose)                         │
│   HomeScreen / SearchScreen / DetailScreen                     │
│   - Renders state visually using Material 3 components         │
│   - Emits user events & actions to ViewModel                    │
└───────────────────────────────▲─────────────────────────────────┘
                                │ Observes StateFlow<UiState>
                                │ Emits User Actions
┌───────────────────────────────▼─────────────────────────────────┐
│                        VIEWMODEL (Hilt)                         │
│   HomeViewModel / SearchViewModel / DetailViewModel            │
│   - Manages UI state using StateFlow / MutableStateFlow          │
│   - Executes business & presentation logic in Coroutines       │
└───────────────────────────────▲─────────────────────────────────┘
                                │ Calls API Services
                                │ Returns DTO Data
┌───────────────────────────────▼─────────────────────────────────┐
│                          MODEL (Data)                           │
│   MoviesApi / GenresApi / Ktor Client / DTOs                   │
│   - Handles network requests & response parsing                 │
│   - Returns type-safe domain models & data transfers           │
└─────────────────────────────────────────────────────────────────┘
```

- **View Layer**: Pure Jetpack Compose screens (`HomeScreen`, `SearchScreen`, `DetailScreen`) that
  observe UI state asynchronously using `collectAsStateWithLifecycle()` and display UI accordingly.
- **ViewModel Layer**: Jetpack ViewModels annotated with `@HiltViewModel` (`HomeViewModel`,
  `SearchViewModel`, `DetailViewModel`) that hold state and survive configuration changes.
- **Model / Data Layer**: Network services (`MoviesApi`, `GenresApi`) powered by Ktor Client
  fetching and parsing response DTOs.
- **Unidirectional Data Flow (UDF)**: UI state flows down from ViewModel to View, and user actions
  flow up from View to ViewModel.

### 🧰 Tech Stack Table

| Layer                    | Technologies / Libraries                                                   |
|:-------------------------|:---------------------------------------------------------------------------|
| **Architecture**         | **MVVM**, ViewModel, StateFlow, Coroutines, Unidirectional Data Flow (UDF) |
| **UI & Layout**          | Jetpack Compose (Material 3), Custom Theme & Typography                    |
| **Navigation**           | Navigation Compose (Type-safe routes with `@Serializable`)                 |
| **Dependency Injection** | Dagger Hilt & KSP                                                          |
| **Networking**           | Ktor Client (OkHttp engine, ContentNegotiation, Logging)                   |
| **JSON Serialization**   | Kotlinx Serialization                                                      |
| **Image Loading**        | Coil 3 (Compose & OkHttp integrations)                                     |
| **API Backend**          | [MoviesAPI.ir](https://moviesapi.ir)                                       |

---

## 📁 Project Structure

```text
com.example.movino
├── core
│   ├── common       # Shared UiState interfaces and status enums
│   ├── navigation   # Type-safe AppRoutes and AppNavigation host
│   └── network      # Ktor HttpClient setup, base URL configuration, and error handlers
├── data
│   ├── api          # MoviesApi & GenresApi Ktor services
│   └── dto          # Data Transfer Objects (MovieDto, GenreDto, MetadataDto, etc.)
├── di               # Hilt modules (NetworkModule, ApiModule)
├── feature
│   ├── detail       # Movie Detail screen, ViewModel, and UiState
│   ├── home         # Home screen, ViewModel, and UiState
│   └── search       # Search screen, ViewModel, and UiState
├── ui
│   ├── components   # Reusable Compose UI widgets (MovieCard, MovieSlider, GenreSlider, SearchBar, etc.)
│   └── theme        # Material 3 Color, Shape, Type, and Theme definitions
└── MainActivity.kt  # Main entry point activity hosting AppNavigation
```

---

## 🚀 Getting Started

### Prerequisites

- **Android Studio**: Ladybug (2024.2.1) or newer recommended.
- **JDK**: Java 11 or higher.
- **Min SDK**: Android 7.0 (API Level 24).
- **Target SDK**: Android 15 (API Level 36).

### Building & Running

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/your-username/movino.git
   cd movino
   ```

2. **Open in Android Studio**:
   Open Android Studio and select **Open**, then navigate to the cloned project folder.

3. **Build the Project**:
   Let Gradle sync and download dependencies, then run:
   ```bash
   ./gradlew assembleDebug
   ```

4. **Run on Emulator / Device**:
   Select an active Android emulator or connected device and press **Run (Shift + F10)**.

---

## 🌐 API Reference

Movino fetches movie data from the free public API [MoviesAPI.ir](https://moviesapi.ir):

- `GET /api/v1/movies?page={page}` - Get paginated list of movies.
- `GET /api/v1/movies?q={query}&page={page}` - Search movies by query.
- `GET /api/v1/genres` - Get list of movie genres.
- `GET /api/v1/genres/{genre_id}/movies` - Get movies filtered by genre ID.
- `GET /api/v1/movies/{movie_id}` - Get detailed information for a specific movie.

---

## 🔮 Roadmap & Upcoming Features

Here are the upcoming features and architectural enhancements planned for future releases:

- 💾 **Room Database**: Local persistence for storing favorite movies, watchlist, and user
  preferences.
- 🔐 **Login & User Authentication**: User authentication flow (login, sign up, session management)
  and user profiles.
- ⚡ **Offline Caching**: Offline-first repository strategy using Room DB and Ktor to provide
  seamless playback and browsing without active network connectivity.
- 📜 **Paging**: Integration of AndroidX Paging 3 library for infinite scrolling pagination of movie
  catalogs and search results.
- 🏗️ **Clean Architecture + Multi-Module Refactoring**: Modularizing the codebase into distinct
  feature and core modules (`:core:model`, `:core:data`, `:core:database`, `:core:network`,
  `:feature:home`, `:feature:detail`, `:feature:login`, etc.) with explicit domain Use Cases.