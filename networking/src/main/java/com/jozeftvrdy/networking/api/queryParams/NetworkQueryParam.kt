package com.jozeftvrdy.networking.api.queryParams

class DefaultQueryParam(
    override val key: String,
    override val value: String
) : NetworkQueryParam

interface NetworkQueryParam {
    val key: String
    val value: String

    operator fun component1(): String = key
    operator fun component2(): String = value
}

typealias NetworkQueryParams = List<NetworkQueryParam>

val EmptyNetworkQueryParams
    get() = emptyList<NetworkQueryParam>()