package windeath44.game.global.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record CursorPage<T> (
        List<T> values,
        Boolean hasNext
) {

    public static <T> CursorPage<T> from(int size, List<T> data) {
        boolean hasNext = data.size() > size;
        return new CursorPage<>(data, hasNext);
    }

}
