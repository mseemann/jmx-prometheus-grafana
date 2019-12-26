package io.mseemann.medium.jmxpg

import io.mseemann.medium.jmxpg.beans.AppInfo
import java.time.Duration

class AppStateSimulation(private val appInfo: AppInfo) : Runnable {

    override fun run() {
        while (true) {
            Thread.sleep(Duration.ofSeconds(10).toMillis())
            appInfo.status = if (appInfo.status == "running") "failed" else "running"
        }
    }

}
