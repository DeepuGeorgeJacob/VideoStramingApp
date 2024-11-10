# Video Player App with ExoPlayer (Compose & Kotlin)

This is a simple **video player** application demonstrating video playback, content stitching by using **ExoPlayer**, **Jetpack Compose**, and **Kotlin**. The app supports both local and streaming content and progress tracking. It also uses a **SharedFlow** to trigger a one-time event, such as displaying a **Toast** message when a specific event occurs.

## Features

- **Video Playback**:
    - Supports playing both **local** and **streaming** videos using **ExoPlayer**, But now configured only for streaming conents.
    - Basic playback controls (play/pause).
- **Content Stitching**:
    - Simulates ad insertion during playback:
        - Main content video plays.
        - At a predetermined timestamp (e.g., 30 seconds), an **ad video** is inserted.
        - After the ad finishes, the main content video resumes.
- **Toast Notifications**:
    - Uses **SharedFlow** to trigger a **Toast** message after the ad is played.
- **Streaming Content**:
    - Supports both local files (e.g., MP4) and streaming URLs.
- **Progress Tracking**:
    - Displays the current playback progress and allows tracking of video position.
- **Multi Module Application**
    - Android application for better maintainability, modularity, and scalability. The modules are organized based on functionality, ensuring a clean separation of concerns.

## Setup Instructions

### 1. Clone the Repository

Clone the repository to your local machine using Git:

#### 2. Open the Project in Android Studio
1. Open Android Studio.
   Select Open an existing project and choose the cloned project directory.
3. Install Dependencies
   The project uses ExoPlayer for video playback and Jetpack Compose for UI. Sync the project with Gradle by clicking on `File > Sync Project with Gradle Files in Android Studio`.

#### 4. Build the APK
**To build the APK Using Android Studio:**

In Android Studio, click `Build > Build APK(s)`.
After the build completes, the APK file can be found in the `build/outputs/apk/` directory.

#### 5. Run the App
Connect an Android device or start an Android Emulator.
Click on the Run button in Android Studio to install and run the app on the device.
