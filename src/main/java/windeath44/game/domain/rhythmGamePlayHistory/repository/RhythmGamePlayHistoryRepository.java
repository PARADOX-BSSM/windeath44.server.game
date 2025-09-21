package windeath44.game.domain.rhythmGamePlayHistory.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import windeath44.game.domain.rhythmGamePlayHistory.model.RhythmGamePlayHistory;

import java.util.List;
import java.util.Optional;

@Repository
public interface RhythmGamePlayHistoryRepository extends JpaRepository<RhythmGamePlayHistory, Long> {
    
    // 나의 게임 플레이 기록 조회 (cursor 기반 페이지네이션)
    @Query("SELECT g FROM RhythmGamePlayHistory g WHERE g.userId = :userId AND (:cursorId IS NULL OR g.gamePlayHistoryId < :cursorId) ORDER BY g.gamePlayHistoryId DESC")
    Slice<RhythmGamePlayHistory> findByUserIdWithCursor(@Param("userId") String userId,
                                                  @Param("cursorId") Long cursorId, 
                                                  Pageable pageable);
    
    // 전체 게임 플레이 기록 조회 (cursor 기반 페이지네이션)
    @Query("SELECT g FROM RhythmGamePlayHistory g WHERE (:cursorId IS NULL OR g.gamePlayHistoryId < :cursorId) ORDER BY g.gamePlayHistoryId DESC")
    Slice<RhythmGamePlayHistory> findAllWithCursor(@Param("cursorId") Long cursorId,
                                             Pageable pageable);

    // 특정 음악에 대한 유저의 최고 기록들을 집계 (병합된 최고 기록을 위함)
    @Query(value = """
        SELECT
            MAX(completion_rate) as completion_rate,
            MAX(rating) as rating,
            MAX(combo) as combo,
            MAX(perfect_plus) as perfect_plus,
            MAX(perfect) as perfect,
            MAX(great) as great,
            MAX(good) as good,
            MIN(miss) as miss,
            (SELECT state FROM rhythm_game_play_history
             WHERE user_id = :userId AND music_id = :musicId
             ORDER BY CASE
                WHEN state = 'ALL_PERFECT' THEN 3
                WHEN state = 'FULL_COMBO' THEN 2
                WHEN state = 'CLEAR' THEN 1
                ELSE 0
             END DESC LIMIT 1) as best_state,
            :userId as user_id,
            :musicId as music_id
        FROM rhythm_game_play_history
        WHERE user_id = :userId AND music_id = :musicId
        """, nativeQuery = true)
    Optional<Object[]> findBestMergedRecordByUserIdAndMusicId(@Param("userId") String userId, @Param("musicId") Long musicId);
}