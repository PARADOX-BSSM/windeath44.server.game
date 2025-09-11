package windeath44.game.global.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import windeath44.game.global.error.exception.ErrorCode;
import windeath44.game.global.error.exception.GlobalException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ErrorResponse globalExceptionHandler(GlobalException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.error("error message : {}", errorCode.getMessage());
        log.error("error status : {}", errorCode.getStatus());

        return ErrorResponse.builder()
                .message(e.getErrorCode().getMessage())
                .status(e.getErrorCode().getStatus())
                .build();
    }
}
