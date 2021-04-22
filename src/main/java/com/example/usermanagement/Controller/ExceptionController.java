package com.example.usermanagement.Controller;

import com.example.usermanagement.Exception.BadRequestException;
import com.example.usermanagement.Exception.BaseRestException;
import com.example.usermanagement.Exception.RestException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = BaseRestException.class)
    public final ResponseEntity<RestException> exception(BaseRestException baseRestException) {
        RestException restException = new RestException(baseRestException.getHttpStatus(), baseRestException.getErrorMessage());
        return ResponseEntity.status(restException.getHttpStatus()).body(restException);
    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public final ResponseEntity<RestException> validationErrorResponse(MethodArgumentNotValidException e){
        BindingResult result = e.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        BadRequestException exception = new BadRequestException(fieldErrors.get(0).getDefaultMessage());
        RestException container = new RestException(exception.getHttpStatus(), exception.getErrorMessage());
        return ResponseEntity.status(container.getHttpStatus()).body(container);
    }
}
