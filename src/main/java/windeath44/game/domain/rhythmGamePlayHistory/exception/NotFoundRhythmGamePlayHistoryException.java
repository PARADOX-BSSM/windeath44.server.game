package windeath44.game.domain.rhythmGamePlayHistory.exception;

import windeath44.game.global.error.exception.ErrorCode;
import windeath44.game.global.error.exception.GlobalException;

public class NotFoundRhythmGamePlayHistoryException extends GlobalException {
    private NotFoundRhythmGamePlayHistoryException() {
        super(ErrorCode.NOT_FOUND_RHYTHM_GAME_PLAY_HISTORY);
    }

    public static class Holder {
        private final static NotFoundRhythmGamePlayHistoryException INSTANCE = new NotFoundRhythmGamePlayHistoryException();
    }

    public static NotFoundRhythmGamePlayHistoryException getInstance() {
        return Holder.INSTANCE;
    }

}
