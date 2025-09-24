package windeath44.game.domain.player.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PlayerResponse(
    String playerId,
    float rating,
    Long ranking,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}