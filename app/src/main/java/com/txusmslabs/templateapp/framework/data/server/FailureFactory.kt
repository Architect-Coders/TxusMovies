package com.txusmslabs.templateapp.framework.data.server

import com.txusmslabs.data.exception.Failure
import java.net.HttpURLConnection.HTTP_BAD_REQUEST

open class FailureFactory {

    open fun handleCode(code: Int) =
        when(code) {
            HTTP_BAD_REQUEST -> Failure.ServerError
            else -> Failure.ApiFailure(code)
        }

    open fun handleException(e: Exception? = null) = Failure.UnexpectedFailure
}