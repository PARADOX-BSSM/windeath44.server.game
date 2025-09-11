package windeath44.game.domain.ranking.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record RankingRequest(
    @NotNull Long musicId,
    LocalDateTime lastUpdatedAt,
    Long lastRank,
    @Positive Integer size
) {
}