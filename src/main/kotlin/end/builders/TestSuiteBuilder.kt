package main.kotlin.end.builders

import main.kotlin.end.annotations.TestDsl
import main.kotlin.end.models.Test
import main.kotlin.end.models.TestSuite

@TestDsl
class TestSuiteBuilder(private val title: String) {
    private val tests = mutableListOf<Test>()

    fun test(title: String, block: TestBuilder.() -> Unit) {
        tests += TestBuilder(title).apply(block).build()
    }

    fun build(): TestSuite {
        return TestSuite(title, tests)
    }
}

fun suite(title: String, block: TestSuiteBuilder.() -> Unit): TestSuite = TestSuiteBuilder(title).apply(block).build()