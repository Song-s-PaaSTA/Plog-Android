package com.kpaas.plog.util

fun splitAddress(address: String): Pair<String, String> {
    val index = address.indexOf("구")
    val first = address.substring(0, index + 1).trim()
    val second = address.substring(index + 1).trim()
    return Pair(first, second)
}