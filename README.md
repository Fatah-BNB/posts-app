# PostsApp

A modern Android application built with Java that displays a list of posts from a remote API.

## 🚀 Build Instructions

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/Fatah-BNB/posts-app.git
   ```
2. **Open in Android Studio**:
   - Launch Android Studio and select **Open**.
   - Navigate to the project root directory.
3. **Configure Environment**:
   - Ensure you have a `local.properties` file in the root directory.
   - Add the following line:
     ```properties
     BASE_URL=https://jsonplaceholder.typicode.com/
     ```
4. **Gradle Sync**:
   - Wait for the Gradle synchronization to complete (File > Sync Project with Gradle Files).
5. **Run**:
   - Select an emulator or physical device.
   - Click the **Run** button or press `Shift + F10`.

## 🛠️ Development Environment

- **Android Studio Version**: Android Studio Quail 2 | 2026.1.2
- **JDK Version**: 18
- **Target SDK**: 37
- **Min SDK**: 29 (Strikes an optimal balance by supporting over 90% of active Android devices while eliminating legacy workaround code for older platform behaviors)

## 🧠 Technical Choices

- **Clean Architecture**: The project is divided into **Data**, **Domain**, and **Presentation** layers to ensure separation of concerns, scalability, and testability.
- **Dependency Injection (Hilt)**: Used for managing dependencies and simplifying class construction across the app.
- **Networking (Retrofit & OkHttp)**: Chosen for efficient and flexible handling of API requests and JSON parsing.
- **View Binding**: Used to securely interact with XML layouts without `findViewById`.
- **Navigation Component**: Manages fragment transitions and back-stack handling within a single activity.
- **Resource Wrapper**: A generic class to manage UI states (Loading, Success, Error) consistently.

## 📈 Possible Improvements

- **Local Persistence (Room)**: Implementing a local database to cache posts for offline viewing.
- **Unit & UI Testing**: Increasing code coverage with JUnit, Mockito, and Espresso tests for all layers.
- **Pagination**: Implementing the Paging library to handle large datasets efficiently.
- **CI/CD**: Setting up GitHub Actions for automated building and testing.
