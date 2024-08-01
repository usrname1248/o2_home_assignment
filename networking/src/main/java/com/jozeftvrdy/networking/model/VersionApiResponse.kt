package com.jozeftvrdy.networking.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VersionApiResponse(@SerialName("android") val android: Long)