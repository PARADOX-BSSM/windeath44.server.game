package windeath44.game.global.dto;

public record ResponseDto<T> (
        String message,
        T data
) {
}
