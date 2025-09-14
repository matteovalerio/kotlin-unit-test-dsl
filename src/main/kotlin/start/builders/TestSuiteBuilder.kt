package main.kotlin.start.builders

import main.kotlin.start.models.Test
import main.kotlin.start.models.TestSuite

class SuiteBuilder(private val title: String) {
    private val _tests = mutableListOf<Test>()

    fun test(title: String, block: TestBuilder.() -> Unit) {
        _tests += TestBuilder(title).apply(block).build()
    }

    fun build(): TestSuite = TestSuite(title, _tests.toList())
}

fun suite(title: String, block: SuiteBuilder.() -> Unit): TestSuite =
    SuiteBuilder(title).apply(block).build()