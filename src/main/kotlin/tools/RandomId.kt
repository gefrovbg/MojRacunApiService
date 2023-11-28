package tools

import kotlin.random.Random

fun generateRandomId(): Long {
    val min = 1_000_000_000_000_000L // Минимальное 16-значное число
    val max = 9_999_999_999_999_999L // Максимальное 16-значное число

    return Random.nextLong(min, max)
}