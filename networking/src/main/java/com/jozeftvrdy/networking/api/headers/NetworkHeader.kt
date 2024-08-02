package com.jozeftvrdy.networking.api.headers

interface NetworkHeader {
    val key: String
    val value: String

    operator fun component1(): String = key
    operator fun component2(): String = value
}

typealias NetworkHeaders = List<NetworkHeader>

val EmptyNetworkHeaders
    get() = emptyList<NetworkHeader>()