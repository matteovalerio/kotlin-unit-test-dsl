package main.kotlin.end

import main.kotlin.end.builders.suite
import main.kotlin.end.utils.shouldBe
import main.kotlin.end.utils.shouldBeNull
import main.kotlin.end.utils.shouldContain
import main.kotlin.end.utils.shouldNotBe
import main.kotlin.end.utils.shouldThrow
import java.io.InvalidClassException


fun main() {
    val s = suite("Demo DSL Test") {
        test("Basic sum tests") {
            should("1 + 1 should be 2") {
                1 + 1 shouldBe 2
            }
            should("1 + 1 should be 3") {
                1 + 1 shouldBe 3
            }
        }

        test("Nullability tests") {
            should("null should be null") {
                val test: String? = null
                test.shouldBeNull()
            }
            should("DevFest should not be null") {
                "DevFest".shouldNotBe(null)
            }
        }

        test("Should contain") {
            should("DevFest should contain est") {
                "DevFest" shouldContain "est"
            }
            should("[1,2,3] should contain 3") {
                listOf(1,2,3) shouldContain 3
            }
        }

        test("Exceptions ") {
            should("listOf(1).[2] throw IndexOutOfBounds") {
                shouldThrow<IndexOutOfBoundsException> {
                    listOf(1)[2]
                }
            }
            should("listOf(1).[2] throw InvalidClassException") {
                shouldThrow<InvalidClassException> {
                    listOf(1)[2]
                }
            }
        }
    }

    Runner.run(s)
}
