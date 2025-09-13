package main.kotlin.end.utils

import main.kotlin.end.models.AssertionResult

fun ok(msg: String) = AssertionResult(msg, isSuccess = true)

fun fail(msg: String) = AssertionResult(msg, isSuccess = false)