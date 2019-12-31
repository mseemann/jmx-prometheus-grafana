package io.mseemann.medium.jmxpg

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.management.ManagementFactory
import java.time.Duration
import javax.management.ObjectName
import kotlin.random.Random

const val NAMESPACE = "jmxpg.mbeans"

fun main() = runBlocking {

    val appInfo = AppInfo()

    val getRequests = Requests()

    val postRequests = Requests()

    ManagementFactory.getPlatformMBeanServer().apply {

        val version = AppInfo::class.java.getPackage().implementationVersion ?: "n.n."
        registerMBean(appInfo, ObjectName("$NAMESPACE:type=AppInfo,version=$version"))

        registerMBean(getRequests, ObjectName("$NAMESPACE:type=Requests,verb=GET"))
        registerMBean(postRequests, ObjectName("$NAMESPACE:type=Requests,verb=POST"))
    }

    // start some simulations
    val job = launch {
        while (true) {
            delay(Duration.ofSeconds(10).toMillis())
            appInfo.status = if (appInfo.status == "running") "failed" else "running"
        }
    }

    launch {
        while (true) {
            delay(Random.nextLong(50))
            getRequests.requestCount++
        }
    }

    launch {
        while (true) {
            delay(Random.nextLong(150))
            postRequests.requestCount++
        }
    }

    job.join()
}
