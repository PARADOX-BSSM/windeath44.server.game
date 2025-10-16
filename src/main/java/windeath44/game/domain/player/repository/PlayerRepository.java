package windeath44.game.domain.player.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import windeath44.game.domain.player.dto.projection.PlayerRankingProjection;
import windeath44.game.domain.player.model.Player;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, String> {

    Optional<Player> findByPlayerId(String playerId);

    @Query(value = """
    SELECT COALESCE(SUM(sub.rating), 0) AS total_rating
    FROM (
        SELECT h.music_id, MAX(h.rating) AS rating, MAX(h.played_at) AS played_at
        FROM rhythm_game_play_history h
        WHERE h.user_id = :userId
        GROUP BY h.music_id
        ORDER BY MAX(h.played_at) DESC
        LIMIT 30
    ) AS sub
    """, nativeQuery = true)
    Float calculatePlayerRatingFromRecentGames(@Param("userId") String userId);


    // 전체 플레이어 랭킹 조회 (cursor 기반 페이지네이션, 순위 포함)
    @Query(value = """
        WITH ranked_players AS (
            SELECT
                p.player_id,
                p.rating,
                p.created_at,
                p.updated_at,
                ROW_NUMBER() OVER (ORDER BY p.rating DESC, p.player_id ASC) AS ranking
            FROM player p
        )
        SELECT
            rp.player_id AS playerId,
            rp.rating,
            rp.ranking,
            rp.created_at AS createdAt,
            rp.updated_at AS updatedAt
        FROM ranked_players rp
        WHERE (:cursorRanking IS NULL OR rp.ranking > :cursorRanking)
        ORDER BY rp.ranking ASC
        """, nativeQuery = true)
    Slice<PlayerRankingProjection> findAllPlayersRankingWithCursor(@Param("cursorRanking") Long cursorRanking,
                                                                   Pageable pageable);
}