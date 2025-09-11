package windeath44.game.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    NOT_FOUND_GAME_PLAY_HISTORY("not found game play history",404);
    
    private final String message;
    private final int status;
}
