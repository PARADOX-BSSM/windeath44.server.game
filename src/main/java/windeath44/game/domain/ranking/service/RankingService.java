package windeath44.game.domain.ranking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import windeath44.game.domain.gamePlayHistory.event.GamePlayHistorySavedEvent;
import windeath44.game.domain.gamePlayHistory.model.RhythmRanking;
import windeath44.game.domain.gamePlayHistory.repository.RhythmRankingRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RankingService {

    private final RhythmRankingRepository rhythmRankingRepository;

    @EventListener
    @Transactional
    public void handleGamePlayHistorySaved(GamePlayHistorySavedEvent event) {
        log.info("Processing ranking update for userId: {}, musicId: {}", 
                event.userId(), event.musicId());

        rhythmRankingRepository.findByUserIdAndMusicId(String.valueOf(event.userId()), event.musicId())
                .ifPresentOrElse(
                    ranking -> updateExistingRanking(ranking, event),
                    () -> createNewRanking(event)
                );
    }

    private void updateExistingRanking(RhythmRanking ranking, GamePlayHistorySavedEvent event) {
        if (ranking.getCompletionRate() < event.completionRate()) {
            RhythmRanking updatedRanking = RhythmRanking.builder()
                    .rankingId(ranking.getRankingId())
                    .userId(ranking.getUserId())
                    .musicId(ranking.getMusicId())
                    .completionRate(event.completionRate())
                    .build();
            
            rhythmRankingRepository.save(updatedRanking);
            log.info("Updated ranking for userId: {}, musicId: {} with completion rate: {}", 
                    event.userId(), event.musicId(), event.completionRate());
        }
    }

    private void createNewRanking(GamePlayHistorySavedEvent event) {
        RhythmRanking newRanking = RhythmRanking.builder()
                .userId(String.valueOf(event.userId()))
                .musicId(event.musicId())
                .completionRate(event.completionRate())
                .build();

        rhythmRankingRepository.save(newRanking);
        log.info("Created new ranking for userId: {}, musicId: {} with completion rate: {}", 
                event.userId(), event.musicId(), event.completionRate());
    }
}