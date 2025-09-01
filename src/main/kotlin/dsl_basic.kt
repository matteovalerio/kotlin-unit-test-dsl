package main.kotlin

fun test(name: String, block: () -> Unit) {
    println("Running test: $name")
    block()
}

fun main() {
    test("Calculator") {
        if(2 + 2 != 4) error("Expected 4")
        println("✅ should add numbers")
    }
}