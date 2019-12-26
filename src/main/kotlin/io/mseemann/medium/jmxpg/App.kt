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

    val mbs = ManagementFactory.getPlatformMBeanServer()

    val appInfo = AppInfo("starting")
    val keyValues = Hashtable<String, String>()
    keyValues["type"] = "AppInfo"
    keyValues["Version"] = App::class.java.getPackage().implementationVersion ?: "n.n."
    mbs.registerMBean(appInfo, ObjectName("jmxpg.mbeans", keyValues))

    val getRequests = Requests()
    getRequests.requestCount = 2
    mbs.registerMBean(getRequests, ObjectName(GET_REQUESTS_O_NAME))

    val postRequests = Requests()
    mbs.registerMBean(postRequests, ObjectName(POST_REQUESTS_O_NAME))

    // start a small simulation
    Thread(AppStateSimulation(appInfo)).start()
    Thread(RequestSimulation(getRequests, 50)).start()
    Thread(RequestSimulation(postRequests, 150)).start()

    //Runtime.getRuntime().addShutdownHook(Thread(Runnable { appInfo.status = "exited" }))
    Thread.sleep(Int.MAX_VALUE.toLong())
}
