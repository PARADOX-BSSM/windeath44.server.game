package windeath44.game.domain.rhythmGamePlayHistory.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import windeath44.game.domain.ranking.model.RhythmRanking;
import windeath44.game.domain.ranking.dto.projection.RankingProjection;

import java.util.Optional;

@Repository
public interface RhythmRankingRepository extends JpaRepository<RhythmRanking, Long> {

    Optional<RhythmRanking> findByUserIdAndMusicId(String userId, Long musicId);

    @Query(value = """
        WITH ranked_data AS (
            SELECT
                r.ranking_id,                
                r.user_id,         
                r.music_id,         
                r.completion_rate,   
                r.updated_at,        
                ROW_NUMBER() OVER (
                    PARTITION BY r.music_id
                    ORDER BY r.completion_rate DESC, r.updated_at ASC
                )                   AS ranking
            FROM rhythm_ranking r
            WHERE r.music_id = :musicId
        )
        SELECT
            rd.ranking_id,
            rd.user_id,
            rd.music_id,
            rd.completion_rate,
            rd.updated_at,
            rd.ranking
        FROM ranked_data rd
        WHERE rd.ranking > COALESCE(:cursorRank, 0)
        ORDER BY rd.ranking ASC, rd.updated_at ASC
        """, nativeQuery = true)
    Slice<RankingProjection> findRankingByMusicIdWithCursor(
            @Param("musicId") Long musicId,
            @Param("cursorRank") Long cursorRank,
            Pageable pageable
    );
}
