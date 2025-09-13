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
            assert("1 + 1 should be 2") {
                1 + 1 shouldBe 2
            }
            assert("1 + 1 should be 3") {
                1 + 1 shouldBe 3
            }
        }

        test("Nullability tests") {
            assert("null should be null") {
                val test: String? = null
                test.shouldBeNull()
            }
            assert("DevFest should not be null") {
                "DevFest".shouldNotBe(null)
            }
        }

        test("Should contain") {
            assert("DevFest should contain est") {
                "DevFest" shouldContain "est"
            }
            assert("[1,2,3] should contain 3") {
                listOf(1,2,3) shouldContain 3
            }
        }

        test("Exceptions ") {
            assert("listOf(1).[2] throw IndexOutOfBounds") {
                shouldThrow<IndexOutOfBoundsException> {
                    listOf(1)[2]
                }
            }
            assert("listOf(1).[2] throw InvalidClassException") {
                shouldThrow<InvalidClassException> {
                    listOf(1)[2]
                }
            }
        }
    }

    Runner.run(s)
}
