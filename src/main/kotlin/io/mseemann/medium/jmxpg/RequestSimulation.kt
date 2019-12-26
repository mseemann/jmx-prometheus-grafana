package io.mseemann.medium.jmxpg

import io.mseemann.medium.jmxpg.beans.Requests
import java.util.*

class RequestSimulation(private val requests: Requests, private val randomBound: Int) : Runnable {

    override fun run() {
        while (true) {
            Thread.sleep(Random().nextInt(randomBound).toLong())
            requests.requestCount++
        }
    }

}
