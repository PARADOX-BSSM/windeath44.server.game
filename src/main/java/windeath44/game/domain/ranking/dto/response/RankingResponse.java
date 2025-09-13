package windeath44.game.domain.ranking.dto.response;

import lombok.Builder;
import windeath44.game.domain.ranking.model.RhythmRanking;
import windeath44.game.domain.ranking.dto.projection.RankingProjection;

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
    
    public static RankingResponse from(RankingProjection projection) {
        return RankingResponse.builder()
                .rankingId(projection.getRankingId())
                .userId(projection.getUserId())
                .musicId(projection.getMusicId())
                .completionRate(projection.getCompletionRate())
                .rank(projection.getRanking())
                .updatedAt(projection.getUpdatedAt())
                .build();
    }
}