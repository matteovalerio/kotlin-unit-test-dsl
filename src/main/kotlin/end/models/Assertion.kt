package main.kotlin.end.models

data class Assertion(val title: String, val assertion: () -> AssertionResult)
