package io.mseemann.medium.jmxpg

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.management.ManagementFactory
import java.time.Duration
import java.util.*
import javax.management.ObjectName

const val NAMESPACE = "jmxpg.mbeans"
const val GET_REQUESTS_O_NAME = "$NAMESPACE:type=Requests,verb=GET"
const val POST_REQUESTS_O_NAME = "$NAMESPACE:type=Requests,verb=POST"

fun main() {

    val appInfo = AppInfo()

    val getRequests = Requests()

    val postRequests = Requests()

    ManagementFactory.getPlatformMBeanServer().apply {

        val version = AppInfo::class.java.getPackage().implementationVersion ?: "n.n."
        registerMBean(appInfo, ObjectName("$NAMESPACE:type=AppInfo,version=$version"))

        registerMBean(getRequests, ObjectName(GET_REQUESTS_O_NAME))
        registerMBean(postRequests, ObjectName(POST_REQUESTS_O_NAME))
    }

    // start a small simulation

    GlobalScope.launch {
        while (true) {
            delay(Duration.ofSeconds(10).toMillis())
            appInfo.status = if (appInfo.status == "running") "failed" else "running"
        }
    }

    GlobalScope.launch {
        while (true) {
            delay(Random().nextInt(50).toLong())
            getRequests.requestCount++
        }
    }
    
    GlobalScope.launch {
        while (true) {
            delay(Random().nextInt(150).toLong())
            postRequests.requestCount++
        }
    }

    // keep it running
    Thread.sleep(Int.MAX_VALUE.toLong())
}
