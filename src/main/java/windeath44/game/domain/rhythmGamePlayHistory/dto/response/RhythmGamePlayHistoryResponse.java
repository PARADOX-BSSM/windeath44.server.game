package windeath44.game.domain.rhythmGamePlayHistory.dto.response;

import lombok.Builder;
import windeath44.game.domain.rhythmGamePlayHistory.model.type.RhythmGamePlayHistoryState;

import java.time.LocalDateTime;

@Builder
public record RhythmGamePlayHistoryResponse(
    Long gamePlayHistoryId,
    String userId,
    Long musicId,
    float completionRate,
    float rating,
    long combo,
    long perfectPlus,
    long perfect,
    long great,
    long good,
    long miss,
    String rank,
    RhythmGamePlayHistoryState state,
    LocalDateTime playedAt
) {}