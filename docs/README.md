# Android – DuckDuckGo Android App  
**Repository:** `Android`  
**Description:** The official DuckDuckGo Android client – a privacy‑first web browser and search app built with Kotlin, Jetpack Compose, and modern Android architecture components.

---

## Table of Contents
1. [Installation](#installation)  
2. [Usage](#usage)  
3. [API Documentation](#api-documentation)  
4. [Examples](#examples)  
5. [Contributing & Development](#contributing--development)  
6. [License](#license)  

---  

## Installation  

### Prerequisites
| Tool | Minimum Version | Why |
|------|----------------|-----|
| **Android Studio** | **Arctic Fox (2020.3.1)** or newer | Full IDE support for Gradle, Compose preview, and device emulators |
| **JDK** | **11** (OpenJDK) | Required by the Android Gradle plugin |
| **Android SDK** | **API 33 (Android 13)** (compileSdk) <br> **API 21 (Android 5.0)** (minSdk) | Guarantees compatibility with the widest range of devices |
| **Gradle** | **7.5+** (wrapper) | Managed by the repo – no manual install needed |
| **Git** | Any recent version | To clone the repository |

> **Tip:** The project uses **Kotlin 1.9** and **Jetpack Compose 1.6**. Ensure your Android Studio channel (Stable/Canary) supports these versions.

### 1️⃣ Clone the repository  

```bash
git clone https://github.com/duckduckgo/Android.git
cd Android
```

### 2️⃣ Open the project in Android Studio  

* **File → Open…** → select the root folder (`Android`).  
* Android Studio will automatically download the Gradle wrapper and required SDK components.

### 3️⃣ Sync & Build  

```bash
# From the terminal (or Android Studio's Gradle view)
./gradlew clean assembleDebug
```

*The `assembleDebug` task compiles the app and produces an APK at `app/build/outputs/apk/debug/app-debug.apk`.*

### 4️⃣ Run on a device or emulator  

* Connect a physical device (USB debugging enabled) **or** start an Android Virtual Device (AVD) with API 33+.  
* In Android Studio click **Run → Run 'app'** or use the command line:

```bash
./gradlew installDebug
```

### 5️⃣ Optional: Release Build  

```bash
# Generates a signed release APK (you need to provide your keystore)
./gradlew assembleRelease
```

> **Note:** The repository contains a `keystore.properties.example` file. Copy it to `keystore.properties` and fill in your signing credentials before building a release.

---

## Usage  

### Launching the app  

* After installation, tap the **DuckDuckGo** icon.  
* The main screen shows a **search bar** at the top and a **browser view** below.

### Core Features  

| Feature | How to Access | Description |
|---------|---------------|-------------|
| **Private Search** | Type a query in the search bar → **Enter** | Sends the query to DuckDuckGo’s privacy‑preserving endpoint (`https://duckduckgo.com`). No personal data is stored. |
| **Tracker Blocking** | Enabled by default | Blocks known tracking scripts and cookies using the built‑in `TrackerBlockingEngine`. |
| **HTTPS Everywhere** | Automatic | Forces HTTPS for all supported sites. |
| **App Settings** | Hamburger menu → **Settings** | Toggle options for **Theme**, **Default Search Engine**, **Clear Data**, **App Updates**, etc. |
| **Bookmarks & History** | Bottom navigation → **Bookmarks** / **History** | Manage saved pages and view browsing history. |
| **Incognito Mode** | Bottom navigation → **Incognito** | Opens a new tab that never writes to history or cache. |
| **Voice Search** | Tap the microphone icon in the search bar | Uses Android’s SpeechRecognizer; results are sent anonymously to DuckDuckGo. |
| **Dark Mode** | Settings → **Theme** → **Dark** | Follows system UI mode or can be forced. |

### Keyboard shortcuts (when running on a physical keyboard)

| Shortcut | Action |
|----------|--------|
| `Ctrl + L` | Focus the address/search bar |
| `Ctrl + T` | Open a new tab |
| `Ctrl + W` | Close current tab |
| `Ctrl + R` | Reload page |
| `Ctrl + Shift + N` | Open incognito tab |

### Debugging & Logging  

* **Logcat tag:** `DDGApp` – all internal logs are filtered under this tag.  
* To enable **verbose logging**, add `-PenableVerboseLogging=true` to the Gradle command:

```bash
./gradlew assembleDebug -PenableVerboseLogging=true
```

---

## API Documentation  

> The DuckDuckGo Android app is primarily a **client**; most public APIs are internal to the app. However, the repository exposes a **modular SDK** that can be reused by other Android projects (e.g., custom browsers, privacy‑focused widgets). The SDK lives under the `sdk/` module.

### High‑level Modules  

| Module | Package | Purpose |
|--------|---------|---------|
| `app` | `com.duckduckgo.app` | Main application, UI, navigation, DI setup |
| `browser` | `com.duckduckgo.browser` | WebView wrapper, tab management, navigation controller |
| `search` | `com.duckduckgo.search` | Search‑bar UI, query handling, suggestions |
| `tracker` | `com.duckduckgo.tracker` | Tracker blocking engine, block‑list updates |
| `network` | `com.duckduckgo.network` | HTTP client (OkHttp), DNS over HTTPS, certificate pinning |
| `privacy` | `com.duckduckgo.privacy` | Data‑clearing utilities, incognito handling |
| `sdk` | `com.duckduckgo.sdk` | Public SDK – `DuckDuckGoBrowser` component and helper classes |
| `di` | `com.duckduckgo.di` | Dagger/Hilt dependency graph |
| `utils` | `com.duckduckgo.common.utils` | Miscellaneous extensions, logging, coroutine helpers |

### Selected Public Classes (SDK)

| Class | Package | Description |
|-------|---------|-------------|
| `DuckDuckGoBrowser` | `com.duckduckgo.sdk` | A composable that embeds the DuckDuckGo web view with built‑in privacy features. |
| `DuckDuckGoSearchBar` | `com.duckduckgo.sdk` | Jetpack Compose search bar that automatically routes queries to DuckDuckGo. |
| `TrackerBlockingEngine` | `com.duckduckgo.tracker` | Core engine that evaluates URLs against the block‑list. |
| `PrivacyCleaner` | `com.duckduckgo.privacy` | Utility to clear cookies, cache, and local storage. |
| `NetworkClient` | `com.duckduckgo.network` | Configured OkHttp client with DoH, TLS 1.3, and certificate pinning. |
| `AppUpdateManager` | `com.duckduckgo.app.update` | Handles in‑app update checks via the DuckDuckGo update API. |

### Dagger/Hilt Components  

| Component | Scope | Provides |
|-----------|-------|----------|
| `AppComponent` | `@Singleton` | Application‑wide singletons (e.g., `NetworkClient`, `TrackerBlockingEngine`). |
| `ActivityComponent` | `@ActivityScoped` | Activity‑level objects (e.g., `BrowserViewModel`). |
| `FragmentComponent` | `@FragmentScoped` | Fragment‑level dependencies (e.g., `SearchViewModel`). |

### API Reference Generation  

The repository ships with **KDoc** and a Gradle task to generate HTML docs:

```bash
./gradlew dokkaHtml
```

The output lives in `build/dokka/html`. Open `index.html` in a browser to explore the full API.

---

## Examples  

Below are practical snippets that demonstrate how to embed DuckDuckGo components in your own Android project.

### 1️⃣ Embedding the DuckDuckGo Browser in a Compose UI  

```kotlin
// build.gradle (app module)
dependencies {
    implementation(project(":sdk")) // or use the published Maven artifact if available
}

// MainActivity.kt
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.duckduckgo.sdk.DuckDuckGoBrowser
import com.duckduckgo.sdk.DuckDuckGoSearchBar
import com.duckduckgo.sdk.rememberDuckDuckGoBrowserState

class MainActivity : ComponentActivity() {
    override