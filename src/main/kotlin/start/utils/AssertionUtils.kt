package main.kotlin.start.utils

import main.kotlin.start.models.AssertionResult

infix fun <T> T.shouldBe(expected: T): AssertionResult =
    if (this == expected) ok("OK: $this == $expected") else fail("FAIL: $this != $expected")


// TODO (live) implement <T> Collection<T>.shouldContain
// TODO (live) implement shouldThrow