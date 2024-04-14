package com.kostyarazboynik.utils.timer

import java.util.concurrent.TimeUnit

class BenchmarkTimer(
    val name: String = "",
    val creationTime: Long = System.nanoTime(),
) {
    private fun elapsedNanoSeconds(): Long = System.nanoTime() - creationTime

    fun elapsed(): Long = TimeUnit.NANOSECONDS.toMillis(elapsedNanoSeconds())
}
