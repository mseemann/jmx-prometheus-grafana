package io.mseemann.medium.jmxpg

import io.mseemann.medium.jmxpg.beans.AppInfo
import io.mseemann.medium.jmxpg.beans.Requests
import java.lang.management.ManagementFactory
import java.util.*
import javax.management.ObjectName

object App

const val NAMESPACE = "jmxpg.mbeans"
const val GET_REQUESTS_O_NAME = "$NAMESPACE:type=Requests,verb=GET"
const val POST_REQUESTS_O_NAME = "$NAMESPACE:type=Requests,verb=POST"

fun main() {

    val appInfo = AppInfo()

    val getRequests = Requests()

    val postRequests = Requests()

    ManagementFactory.getPlatformMBeanServer().apply {

        val keyValues = Hashtable<String, String>()
        keyValues["type"] = "AppInfo"
        keyValues["Version"] = App::class.java.getPackage().implementationVersion ?: "n.n."
        registerMBean(appInfo, ObjectName(NAMESPACE, keyValues))

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
