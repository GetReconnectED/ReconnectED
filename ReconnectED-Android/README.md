# ReconnectED

<div align="center">
    <img src="https://github.com/GetReconnectED/ReconnectED/raw/main/Assets/logo.png" alt="ReconnectED logo" width="128" />
    <h1>ReconnectED</h1>
</div>

<p align="center"><i>Minimize the overconsumption of the internet.</i></p>

![Lint - Android](https://img.shields.io/github/actions/workflow/status/GetReconnectED/ReconnectED/lint-android.yml?label=Lint%20-%20Android)
![Lint - Web](https://img.shields.io/github/actions/workflow/status/GetReconnectED/ReconnectED/lint-web.yml?label=Lint%20-%20Web)
![Test - Android](https://img.shields.io/github/actions/workflow/status/GetReconnectED/ReconnectED/test-android.yml?label=Test%20-%20Android)
![Test - Web](https://img.shields.io/github/actions/workflow/status/GetReconnectED/ReconnectED/test-web.yml?label=Test%20-%20Web)

ReconnectED is a digital detox planner that helps reduce screen time. It includes a daily planner to set screen time goals, and goal-setting sections.

Assisted by its smart AI Companion App, it provides prompts and support to stay on track.

In today's Digital Age, constant content consumption fuels screen dependency and information overload, affecting well-being and productivity. Rising digital demand harms the environment, while device production depletes natural resources. Let's stay mindful of our digital habits and find inspiration in a balanced, healthier lifestyle.

This repository contains the source code for both the Android and Web applications of ReconnectED.

## Development Environment Setup

### Android Development Environment Setup

1. Install Android Studio.
2. Open the project in Android Studio.
3. Get the Firebase configuration file from the Firebase console and place it in the `ReconnectED-Android/app` directory.
4. Set the SHA-1 certificate fingerprint in the Firebase console.

### Running Tests

#### Android Tests

The Android project includes comprehensive unit tests covering core components, data models, and utilities.

To run all unit tests:
```bash
cd ReconnectED-Android
./gradlew testDebugUnitTest
```

To run tests with coverage:
```bash
./gradlew testDebugUnitTestCoverage
```

Test results are available at `app/build/reports/tests/testDebugUnitTest/index.html`

For more details on the test suite, see [app/src/test/README.md](./app/src/test/README.md).

**Test Coverage:**
- 94 unit tests across 8 test classes
- Coverage areas: utilities, data models, enums, type converters, entities

### License

See [LICENSE](./LICENSE) for details.
