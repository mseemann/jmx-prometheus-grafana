package io.mseemann.medium.jmxpg.beans

interface AppInfoMBean {
    var status: String
}

class AppInfo(override var status: String = "starting") : AppInfoMBean
