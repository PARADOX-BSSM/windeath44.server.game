package windeath44.game.domain.rhythmGamePlayHistory.exception;

import windeath44.game.global.error.exception.ErrorCode;
import windeath44.game.global.error.exception.GlobalException;

public class InvalidGamePlayHistoryDataException extends GlobalException {

    public InvalidGamePlayHistoryDataException() {
        super(ErrorCode.INVALID_GAME_PLAY_HISTORY_DATA);
    }
}