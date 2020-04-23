package com.txusmslabs.data.exception

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
sealed class Failure {
    object NetworkConnection : Failure()
    object ServerError : Failure()
    object UnknownApiError : Failure()
    object UnexpectedFailure : Failure()
    object TokenNotFound : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()

    class ApiFailure(val code: Int = 0) : FeatureFailure() {
        fun type() = Type.build(code)
        sealed class Type {
            object None : Type()
            object Headers : Type()
            object NotAvailable : Type()
            object NotFound : Type()
            object Conflict : Type()
            companion object {
                fun build(code: Int): Type {
                    return when (code) {
                        404 -> NotFound
                        406 -> NotAvailable
                        412 -> Headers
                        409 -> Conflict
                        else -> None
                    }
                }
            }
        }
    }
}