package windeath44.game.domain.gamePlayHistory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import windeath44.game.domain.gamePlayHistory.model.RhythmRanking;

import java.util.Optional;

@Repository
public interface RhythmRankingRepository extends JpaRepository<RhythmRanking, Long> {
    
    Optional<RhythmRanking> findByUserIdAndMusicId(String userId, Long musicId);
}