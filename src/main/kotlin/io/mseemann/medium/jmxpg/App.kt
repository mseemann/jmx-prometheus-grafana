package io.mseemann.medium.jmxpg

import io.mseemann.medium.jmxpg.beans.AppInfo
import io.mseemann.medium.jmxpg.beans.Requests
import java.lang.management.ManagementFactory
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
    Thread(AppStateSimulation(appInfo)).start()
    Thread(RequestSimulation(getRequests, 50)).start()
    Thread(RequestSimulation(postRequests, 150)).start()

    // keep it running
    Thread.sleep(Int.MAX_VALUE.toLong())
}
