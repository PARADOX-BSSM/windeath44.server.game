package windeath44.game.global.dto;

import java.util.List;

public record CursorPage<T> (
        List<T> values,
        Boolean hasNext
) {

}
