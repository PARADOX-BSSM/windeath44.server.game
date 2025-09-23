package windeath44.game.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    NOT_FOUND_RHYTHM_GAME_PLAY_HISTORY("not found rhythm game play history",404),
    INVALID_GAME_PLAY_HISTORY_DATA("invalid game play history data",400);
    
    private final String message;
    private final int status;
}
