package windeath44.game.global.error;

import windeath44.game.global.error.exception.ErrorCode;

public record ErrorResponse (
        int status,
        String message
) {
    public ErrorResponse(ErrorCode errorCode) {
        this(
                errorCode.getStatus(),
                errorCode.getMessage()
        );
    }
}
