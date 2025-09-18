package windeath44.game.domain.ranking.dto.projection;

import java.time.LocalDateTime;

public interface RankingProjection {
    Long getRankingId();
    String getUserId();
    Long getMusicId();
    Float getCompletionRate();
    LocalDateTime getUpdatedAt();
    Long getRanking();
}