package com.jozeftvrdy.networking.model

sealed interface ApiCallResponse<out T> {

    /**
     * Represents successful network responses (2xx)
     */
    class SuccessApiCallResponse<T>(
        val successResponse: T
    ) : ApiCallResponse<T>


    sealed interface FailedApiCallResponse : ApiCallResponse<Nothing> {
        /**
         * Represents failed network responses (3xx, 4xx, 5xx)
         */
        class HttpError(
            val httpCode: Int
        ) : FailedApiCallResponse

        /**
         * Represents failed network issues like SocketTimeOutException
         */
        class NetworkError(
            val reason: Throwable
        ) : FailedApiCallResponse

        /**
         * Represents issues thrown by parsing response
         */
        class SerializationError(
            val reason: Throwable
        ) : FailedApiCallResponse

        class GeneralError(
            val reason: Throwable
        ) : FailedApiCallResponse
    }
}

inline fun <T> ApiCallResponse<T>.ifSuccess(
    action: (T) -> Unit
) = when (this) {
    is ApiCallResponse.FailedApiCallResponse -> {
        this
    }
    is ApiCallResponse.SuccessApiCallResponse -> {
        action(this.successResponse)
        this
    }
}
