package com.jozeftvrdy.networking.ktor

import com.jozeftvrdy.networking.api.headers.EmptyNetworkHeaders
import com.jozeftvrdy.networking.api.headers.NetworkHeaders
import com.jozeftvrdy.networking.api.queryParams.EmptyNetworkQueryParams
import com.jozeftvrdy.networking.api.queryParams.NetworkQueryParams
import com.jozeftvrdy.networking.model.ApiCallResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val baseUrl = "https://api.o2.sk/"

class KtorNetworkCallFactory(
    private val ktorHttpClient: HttpClient
) {

    suspend inline fun <reified T> networkApiCall(
        path: String,
        httpMethod: HttpMethod,
        reqBody: Any? = null,
        headers: NetworkHeaders = EmptyNetworkHeaders,
        queryParams: NetworkQueryParams = EmptyNetworkQueryParams,
    ): ApiCallResponse<T> = apiCall(
        path = path,
        httpMethod = httpMethod,
        reqBody = reqBody,
        headers = headers,
        queryParams = queryParams,
        successCast = {
            val successResponse: T = it.body()
            successResponse
        }
    )


    suspend fun <T> apiCall(
        path: String,
        httpMethod: HttpMethod,
        reqBody: Any? = null,
        headers: NetworkHeaders,
        queryParams: NetworkQueryParams,
        successCast: suspend (HttpResponse) -> T,
    ): ApiCallResponse<T> = internalApiCall(
        successCast = successCast
    ) {
        ktorHttpClient.request(baseUrl + path) {
            method = httpMethod
            contentType(
                ContentType.Application.Json
            )
            reqBody?.let {
                setBody(it)
            }
            headers.forEach { (key, value) ->
                header(key, value)
            }
            queryParams.forEach { (key, value) ->
                parameter(key, value)
            }
        }
    }

    private suspend fun <T> internalApiCall(
        successCast: suspend (HttpResponse) -> T,
        apiCall: suspend () -> HttpResponse
    ): ApiCallResponse<T> = withContext(Dispatchers.IO) {
        try {
            val httpResponse = apiCall.invoke()

            if (httpResponse.status == HttpStatusCode.OK) {
                try {
                    ApiCallResponse.SuccessApiCallResponse(successCast(httpResponse.body()))
                } catch (throwable: Throwable) {
                    ApiCallResponse.FailedApiCallResponse.SerializationError(reason = throwable)
                }
            } else {
                ApiCallResponse.FailedApiCallResponse.HttpError(httpCode = httpResponse.status.value)
            }
        } catch (throwable: Throwable) {
            ApiCallResponse.FailedApiCallResponse.NetworkError(reason = throwable)
        }
    }
}