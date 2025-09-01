package main.kotlin

fun main() {
    val sum = 2 + 2
    if (sum != 4) error("Expected 4 but got $sum")
    println("Test passed!")
}