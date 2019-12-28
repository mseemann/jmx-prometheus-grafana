package io.mseemann.medium.jmxpg

interface RequestsMBean {
    val requestCount: Long
}

class Requests(override var requestCount: Long = 0) : RequestsMBean
