# Kotlin Unit Test DSL

Content for Live at DevFest 2025 - Venice.

The repository contains a simple DSL for writing unit tests in Kotlin and the slides.

## Example Kotlin DSL test
```kotlin
suite("Demo DSL Test") {
    test("Basic sum tests") {
        expect("1 + 1 should be 2") {
            1 + 1 shouldBe 2
        }
        expect("1 + 1 should be 3") {
            1 + 1 shouldBe 3
        }
    }
}
```