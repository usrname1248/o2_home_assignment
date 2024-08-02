package com.jozeftvrdy.networking.api

import com.jozeftvrdy.networking.model.ApiCallResponse
import com.jozeftvrdy.networking.model.VersionApiResponse
import java.util.UUID

interface VersionApi {
    suspend fun getVersion(
        uuid: UUID,
    ): ApiCallResponse<VersionApiResponse>
}