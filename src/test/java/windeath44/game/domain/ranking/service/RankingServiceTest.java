package windeath44.game.domain.ranking.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import windeath44.game.domain.gamePlayHistory.event.GamePlayHistorySavedEvent;
import windeath44.game.domain.gamePlayHistory.model.RhythmRanking;
import windeath44.game.domain.gamePlayHistory.repository.RhythmRankingRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class RankingServiceTest {

    @Autowired
    private RankingService rankingService;

    @Autowired
    private RhythmRankingRepository rhythmRankingRepository;

    @Test
    @DisplayName("새로운 게임 플레이 기록 시 랭킹이 생성된다")
    void createNewRanking() {
        // given
        Long userId = 1L;
        Long musicId = 100L;
        Float completionRate = 95.5f;
        
        GamePlayHistorySavedEvent event = GamePlayHistorySavedEvent.from(
            userId, musicId, completionRate, 150L, 80L, 60L, 10L, 0L
        );

        // when
        rankingService.handleGamePlayHistorySaved(event);

        // then
        Optional<RhythmRanking> ranking = rhythmRankingRepository.findByUserIdAndMusicId(String.valueOf(userId), musicId);
        assertThat(ranking).isPresent();
        assertThat(ranking.get().getCompletionRate()).isEqualTo(completionRate);
    }

    @Test
    @DisplayName("기존 랭킹보다 높은 완성도 기록 시 랭킹이 업데이트된다")
    void updateRankingWithHigherScore() {
        // given
        Long userId = 1L;
        Long musicId = 100L;
        
        // 첫 번째 기록
        GamePlayHistorySavedEvent firstEvent = GamePlayHistorySavedEvent.from(
            userId, musicId, 80.0f, 100L, 50L, 40L, 20L, 10L
        );
        rankingService.handleGamePlayHistorySaved(firstEvent);

        // 더 높은 완성도의 두 번째 기록
        GamePlayHistorySavedEvent secondEvent = GamePlayHistorySavedEvent.from(
            userId, musicId, 95.5f, 150L, 80L, 60L, 10L, 0L
        );

        // when
        rankingService.handleGamePlayHistorySaved(secondEvent);

        // then
        Optional<RhythmRanking> ranking = rhythmRankingRepository.findByUserIdAndMusicId(String.valueOf(userId), musicId);
        assertThat(ranking).isPresent();
        assertThat(ranking.get().getCompletionRate()).isEqualTo(95.5f);
    }

    @Test
    @DisplayName("기존 랭킹보다 낮은 완성도 기록 시 랭킹이 업데이트되지 않는다")
    void doNotUpdateRankingWithLowerScore() {
        // given
        Long userId = 1L;
        Long musicId = 100L;
        
        // 첫 번째 기록 (높은 완성도)
        GamePlayHistorySavedEvent firstEvent = GamePlayHistorySavedEvent.from(
            userId, musicId, 95.5f, 150L, 80L, 60L, 10L, 0L
        );
        rankingService.handleGamePlayHistorySaved(firstEvent);

        // 더 낮은 완성도의 두 번째 기록
        GamePlayHistorySavedEvent secondEvent = GamePlayHistorySavedEvent.from(
            userId, musicId, 80.0f, 100L, 50L, 40L, 20L, 10L
        );

        // when
        rankingService.handleGamePlayHistorySaved(secondEvent);

        // then
        Optional<RhythmRanking> ranking = rhythmRankingRepository.findByUserIdAndMusicId(String.valueOf(userId), musicId);
        assertThat(ranking).isPresent();
        assertThat(ranking.get().getCompletionRate()).isEqualTo(95.5f);
    }

    @Test
    @DisplayName("같은 유저는 한 음악에 하나의 랭킹만 가질 수 있다")
    void oneRankingPerUserPerMusic() {
        // given
        Long userId = 1L;
        Long musicId = 100L;
        
        GamePlayHistorySavedEvent event1 = GamePlayHistorySavedEvent.from(
            userId, musicId, 80.0f, 100L, 50L, 40L, 20L, 10L
        );
        GamePlayHistorySavedEvent event2 = GamePlayHistorySavedEvent.from(
            userId, musicId, 95.5f, 150L, 80L, 60L, 10L, 0L
        );

        // when
        rankingService.handleGamePlayHistorySaved(event1);
        rankingService.handleGamePlayHistorySaved(event2);

        // then
        long rankingCount = rhythmRankingRepository.findAll().stream()
            .filter(r -> r.getUserId().equals(String.valueOf(userId)) && r.getMusicId().equals(musicId))
            .count();
        
        assertThat(rankingCount).isEqualTo(1);
    }
}