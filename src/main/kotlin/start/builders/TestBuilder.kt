package main.kotlin.start.builders

import main.kotlin.start.models.Assertion
import main.kotlin.start.models.AssertionResult
import main.kotlin.start.models.Test

class TestBuilder(private val title: String) {
    private val _assertions = mutableListOf<Assertion>()

    fun should(title: String, block: () -> AssertionResult) {
        TODO("implement this function")
    }

    fun build() = Test(title, _assertions.toList())
}