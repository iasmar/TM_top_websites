package com.iasmar.toronto.data.sources.remote;

/**
 * Created by Asmar on 15/10/2017.
 * <p>
 * Custom Api error class that will hold the error that might accrue while connecting with the request or the response.
 *
 * @author Asmar
 * @version 1
 * @since 0.1.0
 */
public class ApiError extends RuntimeException {
    // The unique code of the error.
    private int errorCode;

    /**
     *Prevent direct instantiation.
     * @param errorCode The unique code of the error.
     */
    public ApiError(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Get the unique code of the error.
     * @return The unique code of the error.
     */
    public int getErrorCode() {
        return errorCode;
    }
}
