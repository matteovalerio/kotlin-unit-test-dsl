package main.kotlin.start.utils

import main.kotlin.start.models.AssertionResult

fun ok(msg: String) = AssertionResult(msg, true)
fun fail(msg: String) = AssertionResult(msg, false)