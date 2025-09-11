package windeath44.game.domain.gamePlayHistory.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import windeath44.game.domain.gamePlayHistory.model.GamePlayHistory;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GamePlayHistoryResponse {
    
    private Long gamePlayHistoryId;
    private Long userId; // 사용자 ID
    private Long musicId;
    private float completionRate;
    private long combo;
    private long perfectPlus;
    private long perfect;
    private long great;
    private long miss;
    private LocalDateTime playedAt;

}