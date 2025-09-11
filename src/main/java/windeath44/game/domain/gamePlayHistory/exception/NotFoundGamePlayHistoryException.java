package windeath44.game.domain.gamePlayHistory.exception;

import windeath44.game.global.error.exception.ErrorCode;
import windeath44.game.global.error.exception.GlobalException;

public class NotFoundGamePlayHistoryException extends GlobalException {
    private NotFoundGamePlayHistoryException() {
        super(ErrorCode.NOT_FOUND_GAME_PLAY_HISTORY);
    }

    public static class Holder {
        private final static NotFoundGamePlayHistoryException INSTANCE = new NotFoundGamePlayHistoryException();
    }

    public static NotFoundGamePlayHistoryException getInstance() {
        return Holder.INSTANCE;
    }

}
