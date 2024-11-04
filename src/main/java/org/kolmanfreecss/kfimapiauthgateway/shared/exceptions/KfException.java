package org.kolmanfreecss.kfimapiauthgateway.shared.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * KfException class.
 * Used to define the methods that the KfException must implement.
 * 
 * @version 1.0
 * @author Kolman-Freecss
 */
@Getter
public class KfException extends RuntimeException {
    
    final Integer httpStatusCode;
    
    final HttpStatus httpStatus;
    
    final String apiMessage;
    
    public KfException(final String message, 
                       final Throwable cause) {
        super(message, cause);
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        httpStatusCode = httpStatus.value();
        apiMessage = "Server error";
    }
    
    public KfException(final String message, 
                       final Throwable cause,
                       final HttpStatus httpStatus) {
        super(message, cause);
        this.httpStatus = httpStatus;
        httpStatusCode = httpStatus.value();
        // TODO: Implement a dict of standard messages
        apiMessage = message;
    }
    
}
