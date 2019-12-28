package io.mseemann.medium.jmxpg

interface AppInfoMBean {
    var status: String
}

class AppInfo(override var status: String = "starting") : AppInfoMBean
