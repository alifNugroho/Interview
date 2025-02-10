package com.alif.project.interview.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ApiTemplateMessage {

    public ResponseEntity<Object> buildResponseEntity(ApiResponseTemplate template) {
        return new ResponseEntity<>(template, HttpStatus.OK);
    }
}
