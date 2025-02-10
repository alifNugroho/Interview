package com.alif.project.interview.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Component
public class ApiErrorTemplateMessage {

    public ResponseEntity<Object> buildResponseEntity(ApiErrorResponseTemplate template, HttpStatus status) {
        return new ResponseEntity<>(template, status);
    }

}
