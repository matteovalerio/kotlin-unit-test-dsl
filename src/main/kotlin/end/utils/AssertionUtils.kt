package main.kotlin.end.utils

import main.kotlin.end.models.AssertionResult

infix fun <T> T.shouldBe(expected: T): AssertionResult {
    if (this == expected) {
        return ok("OK: $this == $expected")
    }
    return fail("Fail: $this != $expected")
}

infix fun <T> T.shouldNotBe(expected: T): AssertionResult {
    if (this == expected) {
        return fail("Fail: $this == $expected")
    }
    return ok("OK: $this != $expected")
}

fun <T> T?.shouldBeNull(): AssertionResult {
    if (this == null) {
        return ok("OK: value is null")
    }
    return fail("Fail: expected null, received $this")
}

infix fun <T> Collection<T>.shouldContain(value: T): AssertionResult {
    return if (this.contains(value)) ok("OK: value contains $value") else fail("Fail: value contains $value")
}

infix fun <T : CharSequence> T.shouldContain(value: CharSequence): AssertionResult {
    return if (this.contains(value)) ok("OK: value contains $value") else fail("Fail: value contains $value")
}

inline fun <reified T : Throwable> shouldThrow(block: () -> Unit): AssertionResult {
    try {
        block.invoke()
        return fail("Fail: expected ${T::class.simpleName} to be thrown")
    } catch (e: Throwable) {
        if (e is T) {
           return ok("Ok: ${T::class.simpleName} thrown")
        }
        return fail("Fail: thrown ${e.javaClass.simpleName} thrown expected ${T::class.simpleName}")
    }
}