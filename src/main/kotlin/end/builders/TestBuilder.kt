package main.kotlin.end.builders

import main.kotlin.end.annotations.TestDsl
import main.kotlin.end.models.Assertion
import main.kotlin.end.models.AssertionResult
import main.kotlin.end.models.Test

@TestDsl
class TestBuilder(private val title: String) {
    private val assertions = mutableListOf<Assertion>()

    fun should(title: String, block: () -> AssertionResult) {
        assertions += Assertion(title, block)
    }

    fun build(): Test {
        return Test(title, assertions.toList())
    }
}