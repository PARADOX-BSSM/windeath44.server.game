package windeath44.game.domain.rhythmGamePlayHistory.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import windeath44.game.domain.rhythmGamePlayHistory.model.RhythmGamePlayHistory;
import windeath44.game.domain.rhythmGamePlayHistory.model.type.RhythmGamePlayHistoryState;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RhythmGamePlayHistoryResponse {
    
    private Long gamePlayHistoryId;
    private String userId;
    private Long musicId;
    private float completionRate;
    private float rating;
    private long combo;
    private long perfectPlus;
    private long perfect;
    private long great;
    private long good;
    private long miss;
    private String rank;
    private RhythmGamePlayHistoryState state;
    private LocalDateTime playedAt;

}