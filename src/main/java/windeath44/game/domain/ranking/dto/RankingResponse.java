package windeath44.game.domain.ranking.dto;

import lombok.Builder;
import windeath44.game.domain.ranking.model.RhythmRanking;

import java.time.LocalDateTime;

@Builder
public record RankingResponse(
    Long rankingId,
    String userId,
    Long musicId,
    Float completionRate,
    Long rank,
    LocalDateTime updatedAt
) {
    
    public static RankingResponse from(RhythmRanking ranking, Long rank) {
        return RankingResponse.builder()
                .rankingId(ranking.getRankingId())
                .userId(ranking.getUserId())
                .musicId(ranking.getMusicId())
                .completionRate(ranking.getCompletionRate())
                .rank(rank)
                .updatedAt(ranking.getUpdatedAt())
                .build();
    }
}