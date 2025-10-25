# Android Unit Tests

This directory contains comprehensive unit tests for the ReconnectED Android application components.

## Test Coverage

### Core Utilities (`core/`)
- **UtilsTest.kt** (8 tests)
  - Tests for `formatTime()` function with various time formats
  - Edge cases: zero time, single units, maximum values, sub-second values

### Data Models (`core/models/`)
- **AppUsageInfoTest.kt** (11 tests)
  - Tests for app usage information data class
  - Validation of properties, equality, copy functionality
  
- **MenusTest.kt** (13 tests)
  - Tests for menu enum and navigation
  - Validation of menu titles and route conversion
  - Error handling for invalid routes

- **ScreensTest.kt** (11 tests)
  - Tests for screen enum and screen name conversion
  - Validation of screen ordering and uniqueness
  - Error handling for invalid screen names

- **ChatTest.kt** (15 tests)
  - Tests for chat message data class
  - User vs system messages
  - Bitmap attachment handling

### Entities (`core/models/entities/`)
- **UserTest.kt** (12 tests)
  - Tests for user entity
  - Validation of required and optional fields
  - Timestamp handling and equality checks

### Legacy Data (`legacy/data/`)
- **ConvertersTest.kt** (9 tests)
  - Tests for Room database type converters
  - List to string and string to list conversions
  - Round-trip conversion validation

- **WeeklyScreenTimeTest.kt** (15 tests)
  - Tests for weekly screen time entity
  - Validation of screen time tracking data
  - Top apps list handling

## Running Tests

### Run all unit tests:
```bash
./gradlew testDebugUnitTest
```

### Run tests with coverage:
```bash
./gradlew testDebugUnitTestCoverage
```

### Run tests for a specific class:
```bash
./gradlew testDebugUnitTest --tests "com.getreconnected.reconnected.core.UtilsTest"
```

### View test results:
Test results are generated in:
- XML: `app/build/test-results/testDebugUnitTest/`
- HTML Report: `app/build/reports/tests/testDebugUnitTest/index.html`

## Test Statistics

- **Total Unit Tests**: 94
- **Test Classes**: 8
- **Code Coverage Areas**:
  - Utility Functions: 100%
  - Data Models: 100%
  - Enums and Helpers: 100%
  - Type Converters: 100%

## CI/CD Integration

Unit tests are automatically run on:
- Pull requests affecting `ReconnectED-Android/**`
- Pushes to `main` and `stable` branches
- Manual workflow dispatch

Test results and reports are uploaded as artifacts in GitHub Actions for review.

## Dependencies

The tests use the following testing frameworks:
- **JUnit 4**: Core testing framework
- **Mockito**: Mocking framework for Android dependencies
- **Android Test Runner**: For instrumented tests

## Writing New Tests

When adding new tests, follow these guidelines:

1. **Naming Convention**: Use descriptive test names: `methodName_condition_expectedResult`
2. **Organize**: Group related tests in the same test class
3. **Coverage**: Aim for edge cases, boundary conditions, and error scenarios
4. **Assertions**: Use appropriate assertions (`assertEquals`, `assertTrue`, etc.)
5. **Documentation**: Add doc comments explaining complex test scenarios

Example:
```kotlin
@Test
fun formatTime_withZeroTime_returnsZeroSeconds() {
    val result = formatTime(0L)
    assertEquals("0s", result)
}
```

## Continuous Improvement

Future test enhancements:
- Add instrumented tests for UI components
- Implement integration tests for database operations
- Add performance benchmarks for critical paths
- Increase coverage for ViewModel classes
