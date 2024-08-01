package com.jozeftvrdy.networking.api

import com.jozeftvrdy.networking.api.queryParams.DefaultQueryParam
import com.jozeftvrdy.networking.ktor.KtorNetworkCallFactory
import com.jozeftvrdy.networking.model.ApiCallResponse
import com.jozeftvrdy.networking.model.VersionApiResponse
import io.ktor.http.HttpMethod
import java.util.UUID

class VersionApi(
    private val networkCallFactory: KtorNetworkCallFactory
) {
    suspend fun getVersion(
        uuid: UUID,
    ): ApiCallResponse<VersionApiResponse> = networkCallFactory.networkApiCall(
            path = "version",
            httpMethod = HttpMethod.Get,
            queryParams = listOf(
                DefaultQueryParam(
                    key = "version",
                    value = uuid.toString()
                )
            )
        )
}