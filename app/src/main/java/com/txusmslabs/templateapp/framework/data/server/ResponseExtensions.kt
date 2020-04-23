package com.txusmslabs.templateapp.framework.data.server

import com.txusmslabs.data.exception.Failure
import com.txusmslabs.data.functional.Either
import retrofit2.Response
import java.io.IOException

inline fun <T, R> Response<T>.safeCall(
    transform: ((T) -> R), //mapper from data layer to domain layer
    errorFactory: FailureFactory = FailureFactory() //error factory
): Either<Failure, R> =
    try {
        when (isSuccessful) {
            true -> {
                body()?.let {
                    Either.Right(transform(it))
                } ?: run {
                    Either.Left(errorFactory.handleCode(code()))
                }
            }
            false -> Either.Left(errorFactory.handleCode(code()))
        }
    } catch (exception: IOException) {
        Either.Left(errorFactory.handleException(exception))
    }

fun <T> Response<T>.safeCallEmpty(
    errorFactory: FailureFactory = FailureFactory() //error factory
): Failure? =
    try {
        when (isSuccessful) {
            true -> null
            false -> errorFactory.handleCode(code())
        }
    } catch (exception: IOException) {
        errorFactory.handleException(exception)
    }