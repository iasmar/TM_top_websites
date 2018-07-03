package com.iasmar.toronto.util;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by Asmar on 15/10/2017.
 * <p>
 * This Class provides utility methods containing the backport of Java 7's Objects utility class.
 * <p>Named as such to avoid clash with java.util.Objects.
 *
 * @author Asmar
 * @since 1.0
 */
public final class ObjectHelper {


    /**
     * Prevent instances of this class.
     */
    @VisibleForTesting
    ObjectHelper() {
        throw new IllegalStateException("No instances!");
    }


    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     *
     * @param reference    an object reference
     * @param errorMessage the exception message to use if the check fails; will be converted to a
     *                     string using {@link String#valueOf(Object)}
     * @return the non-null reference that was validated
     */
    public static <T> T requireNonNull(T reference, @Nullable String errorMessage) {
        return checkNotNull(reference, errorMessage);
    }

    /**
     * Ensures that {@code index} specifies a valid <i>element</i> in an array, list or string of size
     * {@code size}. An element index may range from zero, inclusive, to {@code size}, exclusive.
     *
     * @param index a user-supplied index identifying an element of an array, list or string
     * @param size  the size of that array, list or string
     * @return the value of {@code index}
     */
    public static int checkElementIndex(int index, int size, @Nullable String errorMessage) {
        return com.google.common.base.Preconditions.checkElementIndex(index, size, errorMessage);
    }

    /**
     * Validate that the given value is positive or report an IllegalArgumentException with
     * the parameter name.
     *
     * @param value     the value to validate
     * @param paramName the parameter name of the value
     * @return value
     * @throws IllegalArgumentException if bufferSize &lt;<= 0
     */
    public static int verifyPositive(int value, String paramName) {
        if (value <= 0) {
            throw new IllegalArgumentException(paramName + " > 0 required but it was " + value);
        }
        return value;
    }

    /**
     * Validate that the given value is positive or report an IllegalArgumentException with
     * the parameter name.
     *
     * @param value     the value to validate
     * @param paramName the parameter name of the value
     * @return value
     * @throws IllegalArgumentException if bufferSize &lt;<= 0
     */
    public static long verifyPositive(long value, String paramName) {
        if (value <= 0L) {
            throw new IllegalArgumentException(paramName + " > 0 required but it was " + value);
        }
        return value;
    }

    /**
     * Validate that the given value is negative or report an IllegalArgumentException with
     * the parameter name.
     *
     * @param value     the value to validate
     * @param paramName the parameter name of the value
     * @return value
     * @throws IllegalArgumentException if bufferSize &lt;>= 0
     */
    public static int verifyNegative(int value, String paramName) {
        if (value >= 0) {
            throw new IllegalArgumentException(paramName + " < 0 required but it was " + value);
        }
        return value;
    }

    /**
     * Validate that the given value is negative or report an IllegalArgumentException with
     * the parameter name.
     *
     * @param value     the value to validate
     * @param paramName the parameter name of the value
     * @return value
     * @throws IllegalArgumentException if bufferSize &lt;>= 0
     */
    public static long verifyNegative(long value, String paramName) {
        if (value >= 0L) {
            throw new IllegalArgumentException(paramName + " < 0 required but it was " + value);
        }
        return value;
    }
}
