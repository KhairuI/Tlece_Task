package com.example.tlece_task.base.helper

import com.example.tlece_task.R
import com.example.tlece_task.utils.UiText
import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection

interface NetworkHandlerDataSource {
    suspend fun handleException(e: Exception): UiText
}

class NetworkHandlerDataSourceImpl @Inject constructor() : NetworkHandlerDataSource {

    override suspend fun handleException(e: Exception): UiText {

        val uiText: UiText = when (e) {
            is HttpException -> {
                when (e.code()) {
                    HttpsURLConnection.HTTP_UNAUTHORIZED -> { UiText.StringResource(R.string.http_unauthorized) }
                    HttpsURLConnection.HTTP_FORBIDDEN -> UiText.StringResource(R.string.http_forbidden)
                    HttpsURLConnection.HTTP_INTERNAL_ERROR -> UiText.StringResource(R.string.http_internal_error)
                    HttpsURLConnection.HTTP_BAD_REQUEST -> UiText.StringResource(R.string.http_bad_request)
                    0 -> UiText.StringResource(R.string.no_internet)
                    else -> defaultError()
                }
            }

            is JsonSyntaxException -> UiText.StringResource(R.string.http_json_exception)

            is SocketTimeoutException -> UiText.StringResource(R.string.http_socket_timeout)

            is UnknownHostException -> UiText.StringResource(R.string.http_unknown_host)

            else -> defaultError()
        }
        return uiText
    }

    private fun defaultError() = UiText.StringResource(R.string.api_default_error)

}