package windeath44.game.global.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import windeath44.game.global.error.exception.GlobalException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ErrorResponse globalExceptionHandler(GlobalException e) {
        log.error("GlobalException occurred: ", e);
        return ErrorResponse.builder()
                .message(e.getErrorCode().getMessage())
                .status(e.getErrorCode().getStatus())
                .build();
    }
}
