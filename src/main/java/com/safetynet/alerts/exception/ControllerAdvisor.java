package com.safetynet.alerts.exception;

import com.safetynet.alerts.exception.firestation.NoFirestationFoundException;
import com.safetynet.alerts.exception.person.*;
import com.safetynet.alerts.model.ErrorResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    private static final Logger logger = LogManager.getLogger(ControllerAdvisor.class);

    @ExceptionHandler(NoFirestationFoundException.class)
    @ResponseBody
    public ResponseEntity<?> handleNoFirestationFoundException(NoFirestationFoundException e) {
        logger.error("Firestation not found!");
        return response(new ErrorResponse(404, "FIRESTATION_NOT_FOUND",
                e.getMessage()));
    }

    @ExceptionHandler(NoPersonFoundFromAddressException.class)
    @ResponseBody
    public ResponseEntity<?> handleNoPersonFoundFromAddressException(NoPersonFoundFromAddressException e) {
        logger.error("Person(s) from address not found!");
        return response(new ErrorResponse(404, "PERSON_NOT_FOUND",
                e.getMessage()));
    }

    @ExceptionHandler(NoChildFoundFromAddressException.class)
    @ResponseBody
    public ResponseEntity<?> handleNoChildFoundFromAddressException(NoChildFoundFromAddressException e) {
        logger.error("Person(s) from address not found!");
        return response(new ErrorResponse(404, "CHILD_NOT_FOUND",
                e.getMessage()));
    }

    @ExceptionHandler(NoPersonFoundFromNameException.class)
    @ResponseBody
    public ResponseEntity<?> handleNoPersonFoundFromNameException(NoPersonFoundFromNameException e) {
        logger.error("Person(s) from name not found!");
        return response(new ErrorResponse(404, "PERSON_NOT_FOUND",
                e.getMessage()));
    }

    @ExceptionHandler(NoPersonFoundFromFirstNameAndNameException.class)
    @ResponseBody
    public ResponseEntity<?> handleNoPersonFoundFromFirstNameAndNameException(NoPersonFoundFromFirstNameAndNameException e) {
        logger.error("Person(s) from firstname and lastname not found!");
        return response(new ErrorResponse(404, "PERSON_NOT_FOUND",
                e.getMessage()));
    }

    @ExceptionHandler(NoPersonFoundException.class)
    @ResponseBody
    public ResponseEntity<?> handleNoPersonFoundException(NoPersonFoundException e) {
        logger.error("Person(s) not found!");
        return response(new ErrorResponse(404, "PERSON_NOT_FOUND",
                e.getMessage()));
    }


    protected ResponseEntity<ErrorResponse> response(ErrorResponse errorResponse) {
        HttpStatus status = HttpStatus.resolve(errorResponse.getStatus());
        logger.debug("Responding with a status of {}", status);
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), status);
    }
}