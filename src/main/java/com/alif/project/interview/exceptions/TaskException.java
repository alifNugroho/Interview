package com.alif.project.interview.exceptions;

import java.io.Serial;

public class TaskException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = -6214750714044093088L;


    public TaskException() {
        super();
    }


    public TaskException(String message) {
        super(message);
    }

    public TaskException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskException(Throwable cause) {
        super(cause);
    }
}
