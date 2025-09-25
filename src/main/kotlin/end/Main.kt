package main.kotlin.end

import main.kotlin.end.builders.suite
import main.kotlin.end.utils.shouldBe
import main.kotlin.end.utils.shouldBeNull
import main.kotlin.end.utils.shouldContain
import main.kotlin.end.utils.shouldNotBe
import main.kotlin.end.utils.shouldThrow
import java.io.InvalidClassException

fun main() {
    Runner.run(suite("Demo DSL Test") {
        test("Basic sum tests") {
            expect("1 + 1 should be 2") {
                1 + 1 shouldBe 2
            }
            expect("1 + 1 should be 3") {
                1 + 1 shouldBe 3
            }
        }

        test("Nullability tests") {
            expect("null should be null") {
                val test: String? = null
                test.shouldBeNull()
            }
            expect("DevFest should not be null") {
                "DevFest" shouldNotBe null
            }
        }

        test("Should contain") {
            expect("DevFest should contain est") {
                "DevFest" shouldContain "est"
            }
            expect("[1,2,3] should contain 3") {
                listOf(1, 2, 3) shouldContain 3
            }
        }

        test("Exceptions ") {
            expect("listOf(1).[2] throw IndexOutOfBounds") {
                shouldThrow<IndexOutOfBoundsException> {
                    listOf(1)[2]
                }
            }
            expect("listOf(1).[2] throw InvalidClassException") {
                shouldThrow<InvalidClassException> {
                    listOf(1)[2]
                }
            }
        }
    })
}
