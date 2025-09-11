package windeath44.game.domain.gamePlayHistory.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import windeath44.game.domain.ranking.model.RhythmRanking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RhythmRankingRepository extends JpaRepository<RhythmRanking, Long> {
    
    Optional<RhythmRanking> findByUserIdAndMusicId(String userId, Long musicId);
    
    @Query(value = """
        WITH ranked_data AS (
            SELECT r.*,
                   ROW_NUMBER() OVER (
                       PARTITION BY r.music_id 
                       ORDER BY r.completion_rate DESC, r.updated_at ASC
                   ) as ranking
            FROM rhythm_ranking r
            WHERE r.music_id = :musicId
        )
        SELECT * FROM ranked_data
        WHERE (:lastRank IS NULL OR ranking > :lastRank)
           OR (ranking = :lastRank AND updated_at > :lastUpdatedAt)
        ORDER BY ranking, updated_at
        """, nativeQuery = true)
    List<RhythmRanking> findRankingByMusicIdWithCursor(
        @Param("musicId") Long musicId,
        @Param("lastRank") Long lastRank,
        @Param("lastUpdatedAt") LocalDateTime lastUpdatedAt,
        Pageable pageable
    );
}