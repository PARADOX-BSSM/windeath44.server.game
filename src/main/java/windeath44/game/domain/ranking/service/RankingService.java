package windeath44.game.domain.ranking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import windeath44.game.domain.gamePlayHistory.event.GamePlayHistorySavedEvent;
import windeath44.game.domain.ranking.dto.RankingRequest;
import windeath44.game.domain.ranking.dto.RankingResponse;
import windeath44.game.domain.ranking.model.RhythmRanking;
import windeath44.game.domain.gamePlayHistory.repository.RhythmRankingRepository;
import windeath44.game.global.dto.CursorPage;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RankingService {

    private final RhythmRankingRepository rhythmRankingRepository;

    @Async
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
            ranking.setCompletionRate(event.completionRate());
            rhythmRankingRepository.save(ranking);
            log.info("Updated ranking for userId: {}, musicId: {} with completion rate: {}", 
                    event.userId(), event.musicId(), event.completionRate());
        } else {
            log.info("Ranking not updated for userId: {}, musicId: {} - current rate {} >= new rate {}", 
                    event.userId(), event.musicId(), ranking.getCompletionRate(), event.completionRate());
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
    
    public CursorPage<RankingResponse> getRankingByMusicId(RankingRequest request) {
        List<RhythmRanking> rankings = rhythmRankingRepository.findRankingByMusicIdWithCursor(
            request.musicId(),
            request.lastRank(),
            request.lastUpdatedAt(),
            PageRequest.of(0, request.size() + 1)
        );
        
        AtomicLong rank = new AtomicLong(request.lastRank() != null ? request.lastRank() : 0);
        
        List<RankingResponse> responses = rankings.stream()
            .map(ranking -> RankingResponse.from(ranking, rank.incrementAndGet()))
            .toList();
            
        return CursorPage.from(request.size(), responses);
    }
}