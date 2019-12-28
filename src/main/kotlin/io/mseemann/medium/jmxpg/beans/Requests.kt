package io.mseemann.medium.jmxpg.beans

interface RequestsMBean {
    val requestCount: Long
}

class Requests(override var requestCount: Long = 0) : RequestsMBean
