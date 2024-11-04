package org.kolmanfreecss.kfimapiauthgateway.shared.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * This class is responsible for handling runtime exceptions.
 *
 * @author Kolman-Freecss
 * @version 1.0
 */
@Slf4j
@ControllerAdvice
public class RuntimeExceptionHandler {

    /**
     * Uncontrolled exception handler.
     * Handle runtime exceptions.
     *
     * @param e The runtime exception.
     * @return The response entity.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<KfException> handleRuntimeException(RuntimeException e) {
        final KfException kfException = new KfException("Runtime exception", e);
        log.error(String.format("An error occurred: %s", kfException.getApiMessage()), e);
        return new ResponseEntity<>(kfException, kfException.getHttpStatus());
    }

}
