package com.alif.project.interview.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.id.IdentifierGenerationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

import static org.springframework.http.HttpStatus.OK;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestApiErrorHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(RestApiErrorHandler.class);


    @Autowired
    public RestApiErrorHandler(MessageSource messageSource) {

    }
    /*
      * semua status error diset ke 200 yg membedakan nya hanya status false
      * menyesuaikan dengan UI yg saat hanya bisa menerima 200
     */

    /**
     * Handle NoHandlerFoundException.
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";
        ApiErrorResponseTemplate errorMessage = ErrorUtils.createErrorMessage(false, ex.getMessage());
        return buildResponseEntity(errorMessage, OK);
    }



    /**
     * Handle HttpMediaTypeNotSupportedException. This one triggers when JSON is invalid as well.
     *
     * @param ex      HttpMediaTypeNotSupportedException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        ApiErrorResponseTemplate errorMessage = ErrorUtils.createErrorMessage(false, builder.substring(0, builder.length() - 2));
        return buildResponseEntity(errorMessage, OK);
    }

    /**
     * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid validation.
     *
     * @param ex      the MethodArgumentNotValidException that is thrown when @Valid validation fails
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        ApiErrorResponseTemplate apiError = new ApiErrorResponseTemplate();
        apiError.setMessage("Validation error");
        apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
        apiError.addValidationError(ex.getBindingResult().getGlobalErrors());
        if(!apiError.getSubErrors().isEmpty()){
            StringBuilder messageDefault= new StringBuilder(apiError.getMessage());
            for(ApiSubError subError:apiError.getSubErrors()){
                messageDefault.append(" ").append(subError.toString());
            }
            apiError.setMessage(messageDefault.toString());
        }
        ApiErrorResponseTemplate errorMessage = ErrorUtils.createErrorMessage(false, apiError.getMessage());
        return buildResponseEntity(errorMessage, OK);
    }

    /**
     * Handles javax.validation.ConstraintViolationException. Thrown when @Validated fails.
     *
     * @param ex the ConstraintViolationException
     * @return the ApiError object
     */
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex) {
        ApiErrorResponseTemplate apiError = new ApiErrorResponseTemplate();
        apiError.setMessage("Validation error " +ex.getMessage());
        if(!apiError.getSubErrors().isEmpty()){
            StringBuilder messageDefault= new StringBuilder(apiError.getMessage());
            for(ApiSubError subError:apiError.getSubErrors()){
                messageDefault.append(" ").append(subError.toString());
            }
            apiError.setMessage(messageDefault.toString());
        }
        ApiErrorResponseTemplate errorMessage = ErrorUtils.createErrorMessage(false, apiError.getMessage());
        return buildResponseEntity(errorMessage, OK);
    }

    /**
     * Handles EntityNotFoundException. Created to encapsulate errors with more detail than javax.persistence.EntityNotFoundException.
     *
     * @param ex the EntityNotFoundException
     * @return the ApiError object
     */
    /**
     * Handle HttpMessageNotReadableException. Happens when request JSON is malformed.
     *
     * @param ex      HttpMessageNotReadableException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        log.info("{} to {}", servletWebRequest.getHttpMethod(), servletWebRequest.getRequest().getServletPath());
        String error = "Malformed JSON request";
        ApiErrorResponseTemplate errorMessage = ErrorUtils.createErrorMessage(false, error);
        return buildResponseEntity(errorMessage, OK);
    }

    /**
     * Handle HttpMessageNotWritableException.
     *
     * @param ex      HttpMessageNotWritableException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String error = "Error writing JSON output";
        ApiErrorResponseTemplate errorMessage = ErrorUtils.createErrorMessage(false, error);
        return buildResponseEntity(errorMessage, OK);
    }

    /**
     * Handle NoHandlerFoundException.
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ApiErrorResponseTemplate apiError = new ApiErrorResponseTemplate();
        apiError.setMessage(String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));
        ApiErrorResponseTemplate errorMessage = ErrorUtils.createErrorMessage(false, apiError.getMessage());
        return buildResponseEntity(errorMessage, OK);
    }

    /**
     * Handle javax.persistence.EntityNotFoundException
     */
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        ApiErrorResponseTemplate errorMessage = ErrorUtils.createErrorMessage(false, ex.getMessage());
        return buildResponseEntity(errorMessage, OK);
    }

    /**
     * Handle DataIntegrityViolationException, inspects the cause for different DB causes.
     *
     * @param ex the DataIntegrityViolationException
     * @return the ApiError object
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex,
                                                                  WebRequest request) {
        if (ex.getCause() instanceof ConstraintViolationException) {
            Throwable deepError = ex.getCause();
            if(null!=ex.getCause()){
                if(null!=ex.getCause().getCause()) {
                    if(null!=ex.getCause().getCause().getCause()){
                        if(null!=ex.getCause().getCause().getCause().getCause()){
                            deepError = ex.getCause().getCause().getCause().getCause();
                        }else{
                            deepError = ex.getCause().getCause().getCause();
                        }
                    }else{
                        deepError = ex.getCause().getCause();
                    }

                }
            }
            ApiErrorResponseTemplate errorMessage = ErrorUtils.createErrorMessage(false, "Database error "
                    +deepError.getLocalizedMessage());
            return buildResponseEntity(errorMessage, OK);
        }
        ApiErrorResponseTemplate errorMessage = ErrorUtils.createErrorMessage(false, ex.getMessage()
                +ex);
        return buildResponseEntity(errorMessage, OK);
    }

    /**
     * Handle Exception, handle generic Exception.class
     *
     * @param ex the Exception
     * @return the ApiError object
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                      WebRequest request) {
        ApiErrorResponseTemplate apiError = new ApiErrorResponseTemplate();
        apiError.setMessage(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'", ex.getName(), ex.getValue(), Objects.requireNonNull(ex.getRequiredType()).getSimpleName()));
        ApiErrorResponseTemplate errorMessage = ErrorUtils.createErrorMessage(false, ex.getMessage() +" " +apiError.getMessage());
        return buildResponseEntity(errorMessage, OK);
    }

    @ExceptionHandler(IdentifierGenerationException.class)
    protected ResponseEntity<Object> handleIdentifierGenerationException(IdentifierGenerationException ex,WebRequest request) {
        ApiErrorResponseTemplate apiError = new ApiErrorResponseTemplate();
        apiError.setMessage("Database error");
        ApiErrorResponseTemplate errorMessage = ErrorUtils.createErrorMessage(false, ex.getMessage() +" " +apiError.getMessage());
        return buildResponseEntity(errorMessage, OK);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(Exception ex,
                                                                      WebRequest request) {
        ApiErrorResponseTemplate apiError;
        apiError = new ApiErrorResponseTemplate();
        apiError.setMessage("Exception " + ex.getCause());
        ApiErrorResponseTemplate errorMessage = ErrorUtils.createErrorMessage(false, ex.getMessage());
        return buildResponseEntity(errorMessage, OK);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiErrorResponseTemplate apiError, HttpStatus status) {
        return new ResponseEntity<>(apiError, status);
    }
}
