package apzpzpi215myronovmaksymtask2.secondhandsync.exception.handler;

import apzpzpi215myronovmaksymtask2.secondhandsync.exception.ItemException;
import apzpzpi215myronovmaksymtask2.secondhandsync.model.dto.ErrorDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ItemExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ItemException.class)
    public ResponseEntity<Object> handleCustomException(ItemException exception,
                                                        WebRequest webRequest) {
        var exceptionBody = new ErrorDTO(exception.getName(), exception.getMessage());

        return handleExceptionInternal(exception, exceptionBody, new HttpHeaders(), exception.getResponseStatus(), webRequest);
    }
}
