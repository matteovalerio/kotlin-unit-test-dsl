# Kotlin Unit Test DSL

A simple Kotlin DSL for writing expressive unit tests.

## Example
```kotlin
test("Calculator") {
    should("add numbers") {
        expect(2 + 2).toBe(4)
    }
}
```