package windeath44.game.global.dto;

public record ResponseDto<T> (
        String message,
        T data
) {
    
    public static <T> ResponseDto<T> ok(T data) {
        return new ResponseDto<>("성공", data);
    }
    
    public static <T> ResponseDto<T> ok(String message, T data) {
        return new ResponseDto<>(message, data);
    }
}
