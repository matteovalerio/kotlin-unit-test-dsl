package main.kotlin.start.models

data class Assertion(val title: String, val assertion: () -> AssertionResult)
