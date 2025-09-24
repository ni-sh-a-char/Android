# Android – DuckDuckGo Android App  
**Repository:** `github.com/duckduckgo/android`  
**Description:** The official DuckDuckGo Android client – a privacy‑first web browser, search app, and privacy tools all in one.

---

## Table of Contents
1. [Prerequisites](#prerequisites)  
2. [Installation](#installation)  
   - 2.1 [Clone the Repository](#clone-the-repository)  
   - 2.2 [Open in Android Studio](#open-in-android-studio)  
   - 2.3 [Build & Run](#build--run)  
   - 2.4 [Signing & Release Build](#signing--release-build)  
3. [Usage](#usage)  
   - 3.1 [Launching the App](#launching-the-app)  
   - 3.2 [Main Features Overview](#main-features-overview)  
   - 3.3 [Deep Links & Intents](#deep-links--intents)  
4. [API Documentation](#api-documentation)  
   - 4.1 [Core Packages](#core-packages)  
   - 4.2 [Public Classes & Interfaces](#public-classes--interfaces)  
   - 4.3 [Extension Functions & Utilities](#extension-functions--utilities)  
5. [Examples](#examples)  
   - 5.1 [Programmatic Search](#programmatic-search)  
   - 5.2 [Opening a URL in a Custom Tab](#opening-a-url-in-a-custom-tab)  
   - 5.3 [Using the Tracker Blocking API](#using-the-tracker-blocking-api)  
   - 5.4 [Running UI Tests](#running-ui-tests)  
6. [Contributing](#contributing)  
7. [License](#license)  

---

## Prerequisites
| Tool | Minimum Version | Why |
|------|----------------|-----|
| **Android Studio** | Arctic Fox (2020.3.1) or newer | Full IDE support, Gradle integration |
| **JDK** | 11 (OpenJDK) | Required by the Android Gradle plugin |
| **Android SDK** | API 21 (min) – API 34 (target) | Supports the widest range of devices |
| **Gradle** | 8.0+ (wrapper) | Managed by the repo – no manual install needed |
| **Git** | 2.30+ | For cloning and contributing |

> **Tip:** The repository ships a `gradle-wrapper.properties` file, so you can use the exact Gradle version the project was built with without any extra setup.

---

## Installation

### 1. Clone the Repository
```bash
git clone https://github.com/duckduckgo/android.git
cd android
```

### 2. Open in Android Studio
1. Launch Android Studio.  
2. Choose **File → Open…** and select the root folder (`android`).  
3. Android Studio will automatically sync the Gradle project and download required dependencies.

### 3. Build & Run
#### Debug Build (default)
```bash
# From the terminal (or Android Studio's Run button)
./gradlew assembleDebug
```
- The generated APK will be located at `app/build/outputs/apk/debug/app-debug.apk`.  
- Connect a device or start an emulator, then run:
```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
adb shell am start -n com.duckduckgo.mobile.android/.MainActivity
```

#### Run Directly from Android Studio
- Select **Run → Run 'app'** (or press **Shift+F10**).  
- Choose a device/emulator and Android Studio will handle the build, install, and launch.

### 4. Signing & Release Build
To generate a signed release APK for distribution (e.g., Play Store, F-Droid):

1. **Create a keystore** (if you don’t already have one):
   ```bash
   keytool -genkeypair -v -keystore ddg-release.keystore \
           -alias ddg_release_key -keyalg RSA -keysize 2048 \
           -validity 10000
   ```

2. **Add signing config** to `app/build.gradle.kts` (or use the `signingConfigs` block already present):
   ```kotlin
   signingConfigs {
       create("release") {
           storeFile = file("../ddg-release.keystore")
           storePassword = System.getenv("DDG_KEYSTORE_PASSWORD")
           keyAlias = "ddg_release_key"
           keyPassword = System.getenv("DDG_KEY_PASSWORD")
       }
   }
   ```

3. **Build the release APK**:
   ```bash
   ./gradlew assembleRelease
   ```

   The signed APK will be at `app/build/outputs/apk/release/app-release.apk`.

> **Security note:** Never commit keystore files or passwords. Use environment variables or CI secret stores.

---

## Usage

### Launching the App
- **From the launcher:** Tap the DuckDuckGo icon.  
- **Via ADB (debugging):**
  ```bash
  adb shell am start -n com.duckduckgo.mobile.android/.MainActivity
  ```

### Main Features Overview
| Feature | Description | Where to Find |
|---------|-------------|---------------|
| **Privacy‑First Search** | Encrypted, non‑tracking search powered by DuckDuckGo’s API. | `SearchFragment` |
| **Built‑in Tracker Blocking** | Blocks known trackers in real‑time. | `TrackerBlocking` module |
| **Fire Button** | Clears tabs, cookies, cache, and history in one tap. | `FireButtonViewModel` |
| **Custom Tabs Integration** | Opens external links in a secure Chrome Custom Tab. | `CustomTabActivity` |
| **App Settings** | Fine‑grained privacy controls (e.g., site permissions, auto‑clear). | `SettingsActivity` |
| **Bookmarks & Favorites** | Sync‑able via DuckDuckGo account (optional). | `BookmarksRepository` |
| **Dark Mode & Theming** | Follows system UI mode or user‑selected theme. | `ThemeManager` |

### Deep Links & Intents
The app registers the following intent filters (see `AndroidManifest.xml`):

| Action | URI Scheme | Example |
|--------|------------|---------|
| **Search** | `ddg://search?q=` | `adb shell am start -a android.intent.action.VIEW -d "ddg://search?q=privacy"` |
| **Open URL** | `ddg://open?url=` | `adb shell am start -a android.intent.action.VIEW -d "ddg://open?url=https%3A%2F%2Fexample.com"` |
| **Fire Button** | `ddg://fire` | `adb shell am start -a android.intent.action.VIEW -d "ddg://fire"` |

These can be used by other apps or shortcuts to invoke DuckDuckGo functionality directly.

---

## API Documentation

> The API is primarily internal to the app, but several public‑facing components are documented for developers who wish to embed DuckDuckGo functionality in their own Android projects.

### 1. Core Packages

| Package | Purpose |
|---------|---------|
| `com.duckduckgo.mobile.android` | Top‑level app entry points (activities, fragments). |
| `com.duckduckgo.mobile.android.browser` | Browser engine, tab management, navigation. |
| `com.duckduckgo.mobile.android.tracking` | Tracker detection & blocking logic. |
| `com.duckduckgo.mobile.android.search` | Search UI, query handling, remote API client. |
| `com.duckduckgo.mobile.android.fire` | Fire button implementation (data clearing). |
| `com.duckduckgo.mobile.android.di` | Dagger/Hilt dependency injection modules. |
| `com.duckduckgo.mobile.android.utils` | Miscellaneous helpers (extensions, logging). |

### 2. Public Classes & Interfaces

| Class / Interface | Package | Summary |
|-------------------|---------|---------|
| `DuckDuckGoSearchClient` | `search` | Wrapper around DuckDuckGo’s HTTP search endpoint. Provides `search(query: String, callback: (Result<SearchResult>) -> Unit)`. |
| `TrackerBlocking` | `tracking` | Singleton that exposes `isTracker(url: String): Boolean` and `block(url: String)`. |
| `FireButton` | `fire` | `clearAllData(context: Context, onComplete: () -> Unit)` – clears cookies, cache, history, and local storage. |
| `CustomTabHelper` | `browser` | Utility to launch a URL in a Chrome Custom Tab with optional fallback to in‑app WebView. |
| `ThemeManager`