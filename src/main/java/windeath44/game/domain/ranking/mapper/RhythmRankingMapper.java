package windeath44.game.domain.ranking.mapper;

import org.springframework.stereotype.Component;
import windeath44.game.domain.gamePlayHistory.event.GamePlayHistorySavedEvent;
import windeath44.game.domain.ranking.model.RhythmRanking;

@Component
public class RhythmRankingMapper {
    
    public RhythmRanking toEntity(GamePlayHistorySavedEvent event) {
        return RhythmRanking.builder()
                .userId(String.valueOf(event.userId()))
                .musicId(event.musicId())
                .completionRate(event.completionRate())
                .build();
    }
    
    public RhythmRanking updateEntity(RhythmRanking existingRanking, GamePlayHistorySavedEvent event) {
        return RhythmRanking.builder()
                .rankingId(existingRanking.getRankingId())
                .userId(existingRanking.getUserId())
                .musicId(existingRanking.getMusicId())
                .completionRate(event.completionRate())
                .build();
    }
}