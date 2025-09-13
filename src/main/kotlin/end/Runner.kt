package main.kotlin.end

import main.kotlin.end.models.TestSuite
import main.kotlin.end.models.AssertionResult
import main.kotlin.end.utils.*

object Runner {
    data class RunStats(
        val totalTests: Int,
        val totalAssertions: Int,
        val failedAssertions: Int,
        val durationMs: Long
    )

    fun run(suite: TestSuite): RunStats {
        val start = System.currentTimeMillis()
        println("🔷 Suite: ${suite.title}\n")

        var totalAssertions = 0
        var failed = 0

        suite.tests.forEachIndexed { idx, test ->
            println("  🔹 Test ${idx + 1}: ${test.title}")
            test.assertions.forEach { a ->
                totalAssertions++
                val res = safelyExecute(a.assertion)
                val mark = if (res.isSuccess) "✅" else "❌"
                if (!res.isSuccess) failed++
                println("    $mark ${a.title} — ${res.message}")
            }
            println()
        }

        val duration = System.currentTimeMillis() - start
        val summary = "Tests: ${suite.tests.size}, Assertions: $totalAssertions, Failed: $failed, Time: ${duration}ms"
        val banner = if (failed == 0) "🎉 ALL GREEN" else "⚠️ SOME FAILURES"
        println("———")
        println("$banner — $summary")
        return RunStats(suite.tests.size, totalAssertions, failed, duration)
    }

    private fun safelyExecute(block: () -> AssertionResult): AssertionResult =
        try {
            block()
        } catch (t: Throwable) {
            fail("Exception during assertion: ${t::class.simpleName} — ${t.message}")
        }
}