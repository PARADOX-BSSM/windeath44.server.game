package windeath44.game.domain.player.dto.projection;

import java.time.LocalDateTime;

public interface PlayerRankingProjection {
    String getPlayerId();
    Float getRating();
    Long getRanking();
    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();
}