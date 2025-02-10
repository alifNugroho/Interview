package com.alif.project.interview.exceptions;


public class ErrorUtils {

    private ErrorUtils() {}

    public static ApiErrorResponseTemplate createErrorMessage(final Boolean status, final String messageErr){
        return new ApiErrorResponseTemplate(false, messageErr,null);
    }
}
