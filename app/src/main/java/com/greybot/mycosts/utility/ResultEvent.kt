package com.greybot.mycosts.utility


sealed class ResultEvent<Value> {

    data class success<Value>(val v1: Value) : ResultEvent<Value>()
    data class failure<Value>(val e: Exception) : ResultEvent<Value>()

    /// Returns `true` if the result is a success, `false` otherwise.
    val isSuccess: Boolean
        get() {
            return this is success
        }

    /// Returns `true` if the result is a failure, `false` otherwise.
    val isFailure: Boolean get() = !isSuccess

    /// Returns the associated value if the result is a success, `nil` otherwise.
    val value: Value?
        get() {
            if (this is success)
                return this.v1
            return null
        }

    /// Returns the associated error value if the result is a failure, `nil` otherwise.
    val error: Exception?
        get() {
            if (this is failure)
                return this.e
            return null
        }

    private var hasBeenHandled = false

    /**
     * Returns the value and prevents its use again.
     */
    fun getValueIfNotHandled(): Value? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            this.value
        }
    }

    /**
     * Returns the result and prevents its use again.
     */
    fun getResultIfNotHandled(): ResultEvent<Value>? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            this
        }
    }

    companion object {
        /// Creates a `Result` instance from the result of a closure.
        ///
        /// A failure result is created when the closure throws, and a success result is created when the closure
        /// succeeds without throwing an error.
        ///
        ///     func someString() throws -> String { ... }
        ///
        ///     let result = Result(value: {
        ///         return try someString()
        ///     })
        ///
        ///     // The type of result is Result<String>
        ///
        /// The trailing closure syntax is also supported:
        ///
        ///     let result = Result { try someString() }
        ///
        /// - parameter value: The closure to execute and create the result for.
        fun <Value> create(value: () -> Value): ResultEvent<Value> {
            try {
                return success(value())
            } catch (e: Exception) {
                return failure(e)
            }
        }

        fun <V> initial(value: V): ResultEvent<V> {
            try {
                return success(value)
            } catch (e: Exception) {
                return failure(e)
            }
        }
    }

    /// Returns the success value, or throws the failure error.
    ///
    ///     let possibleString: Result<String> = .success("success")
    ///     try print(possibleString.unwrap())
    ///     // Prints "success"
    ///
    ///     let noString: Result<String> = .failure(error)
    ///     try print(noString.unwrap())
    ///     // Throws error
    fun unwrap(): Value {
        when (this) {
            is success -> return v1
            is failure -> throw e
        }
    }

    /// Evaluates the specified closure when the `Result` is a success, passing the unwrapped value as a parameter.
    ///
    /// Use the `map` method with a closure that does not throw. For example:
    ///
    ///     let possibleData: Result<Data> = .success(Data())
    ///     let possibleInt = possibleData.map { $0.count }
    ///     try print(possibleInt.unwrap())
    ///     // Prints "0"
    ///
    ///     let noData: Result<Data> = .failure(error)
    ///     let noInt = noData.map { $0.count }
    ///     try print(noInt.unwrap())
    ///     // Throws error
    ///
    /// - parameter transform: A closure that takes the success value of the `Result` instance.
    ///
    /// - returns: A `Result` containing the result of the given closure. If this instance is a failure, returns the
    ///            same failure.
    fun <T> map(transform: (Value) -> T): ResultEvent<T> {
        when (this) {
            is success -> return success(transform(v1))
            is failure -> return failure(e)
        }
    }

    /// Evaluates the specified closure when the `Result` is a success, passing the unwrapped value as a parameter.
    ///
    /// Use the `flatMap` method with a closure that may throw an error. For example:
    ///
    ///     let possibleData: Result<Data> = .success(Data(...))
    ///     let possibleObject = possibleData.flatMap {
    ///         try JSONSerialization.jsonObject(with: $0)
    ///     }
    ///
    /// - parameter transform: A closure that takes the success value of the instance.
    ///
    /// - returns: A `Result` containing the result of the given closure. If this instance is a failure, returns the
    ///            same failure.
    fun <T> flatMap(transform: (Value) -> T): ResultEvent<T> {
        when (this) {
            is success -> {
                try {
                    return success(transform(v1))
                } catch (error: Exception) {
                    return failure(error)
                }
            }

            is failure -> return failure(e)
        }
    }

    /// Evaluates the specified closure when the `Result` is a failure, passing the unwrapped error as a parameter.
    ///
    /// Use the `mapError` function with a closure that does not throw. For example:
    ///
    ///     let possibleData: Result<Data> = .failure(someError)
    ///     let withMyError: Result<Data> = possibleData.mapError { MyError.error($0) }
    ///
    /// - Parameter transform: A closure that takes the error of the instance.
    /// - Returns: A `Result` instance containing the result of the transform. If this instance is a success, throws exception
    fun <T : Exception> mapError(transform: (Exception) -> T): ResultEvent<T> {
        when (this) {
            is failure -> return failure(transform(e))
            is success -> throw Exception("Should not be called for success")
        }
    }

    /// Evaluates the specified closure when the `Result` is a failure, passing the unwrapped error as a parameter.
    ///
    /// Use the `flatMapError` function with a closure that may throw an error. For example:
    ///
    ///     let possibleData: Result<Data> = .success(Data(...))
    ///     let possibleObject = possibleData.flatMapError {
    ///         try someFailableFunction(taking: $0)
    ///     }
    ///
    /// - Parameter transform: A throwing closure that takes the error of the instance.
    ///
    /// - Returns: A `Result` instance containing the result of the transform. If this instance is a success, returns
    ///            the same instance.
    fun <T : Exception> flatMapError(transform: (Exception) -> T): ResultEvent<T> {
        when (this) {
            is failure -> {
                try {
                    return failure(transform(e))
                } catch (error: Exception) {
                    return failure(error)
                }
            }
            is success -> throw Exception("Should not be called for success")
        }
    }

    /// Evaluates the specified closure when the `Result` is a success, passing the unwrapped value as a parameter.
    ///
    /// Use the `withValue` function to evaluate the passed closure without modifying the `Result` instance.
    ///
    /// - Parameter closure: A closure that takes the success value of this instance.
    /// - Returns: This `Result` instance, unmodified.
    @Suppress("unused")
    fun withValue(closure: (Value) -> Unit): ResultEvent<Value> {
        if (this is success)
            closure(v1)

        return this
    }

    /// Evaluates the specified closure when the `Result` is a failure, passing the unwrapped error as a parameter.
    ///
    /// Use the `withError` function to evaluate the passed closure without modifying the `Result` instance.
    ///
    /// - Parameter closure: A closure that takes the success value of this instance.
    /// - Returns: This `Result` instance, unmodified.
    fun withError(closure: (Exception) -> Unit): ResultEvent<Value> {
        if (this is failure) {
            closure(e)
        }
        return this
    }

    /// Evaluates the specified closure when the `Result` is a success.
    ///
    /// Use the `ifSuccess` function to evaluate the passed closure without modifying the `Result` instance.
    ///
    /// - Parameter closure: A `Void` closure.
    /// - Returns: This `Result` instance, unmodified.
    fun ifSuccess(closure: () -> Unit): ResultEvent<Value> {
        if (isSuccess) {
            closure()
        }
        return this
    }

    /// Evaluates the specified closure when the `Result` is a failure.
    ///
    /// Use the `ifFailure` function to evaluate the passed closure without modifying the `Result` instance.
    ///
    /// - Parameter closure: A `Void` closure.
    /// - Returns: This `Result` instance, unmodified.
    fun ifFailure(closure: () -> Unit): ResultEvent<Value> {
        if (isFailure) {
            closure()
        }
        return this
    }

}


