package windeath44.game.domain.gamePlayHistory.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import windeath44.game.domain.gamePlayHistory.model.GamePlayHistory;

import java.util.List;

@Repository
public interface GamePlayHistoryRepository extends JpaRepository<GamePlayHistory, Long> {
    
    // 나의 게임 플레이 기록 조회 (cursor 기반 페이지네이션)
    @Query("SELECT g FROM GamePlayHistory g WHERE g.userId = :userId AND (:cursorId IS NULL OR g.gamePlayHistoryId < :cursorId) ORDER BY g.gamePlayHistoryId DESC")
    Slice<GamePlayHistory> findByUserIdWithCursor(@Param("userId") String userId,
                                                  @Param("cursorId") Long cursorId, 
                                                  Pageable pageable);
    
    // 전체 게임 플레이 기록 조회 (cursor 기반 페이지네이션) 
    @Query("SELECT g FROM GamePlayHistory g WHERE (:cursorId IS NULL OR g.gamePlayHistoryId < :cursorId) ORDER BY g.gamePlayHistoryId DESC")
    Slice<GamePlayHistory> findAllWithCursor(@Param("cursorId") Long cursorId,
                                             Pageable pageable);
}