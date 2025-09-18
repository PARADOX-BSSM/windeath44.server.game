package windeath44.game.domain.player.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerResponse {

    private String playerId;
    private float rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}