package windeath44.game.domain.rhythmGamePlayHistory.dto.request;

import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record RhythmGamePlayHistoryRequest(
    @NotNull(message = "Music ID is required") // 음악 ID는 필수입니다
    Long musicId,

    @DecimalMin(value = "0.0", message = "Completion rate must be at least 0%") // 완주율은 0% 이상이어야 합니다
    @DecimalMax(value = "100.0", message = "Completion rate cannot exceed 100%") // 완주율은 100%를 초과할 수 없습니다
    float completionRate,

    @Size(min = 1, max = 10, message = "level must be between 1 and 10")
    int level,

    @Min(value = 0, message = "Combo must be at least 0") // 콤보는 0 이상이어야 합니다
    long combo,

    @Min(value = 0, message = "Perfect Plus must be at least 0") // Perfect Plus는 0 이상이어야 합니다
    long perfectPlus,

    @Min(value = 0, message = "Perfect must be at least 0") // Perfect는 0 이상이어야 합니다
    long perfect,

    @Min(value = 0, message = "Great must be at least 0") // Great는 0 이상이어야 합니다
    long great,

    @Min(value = 0, message = "Good must be at least 0") // Good는 0 이상이어야 합니다
    long good,

    @Min(value = 0, message = "Miss must be at least 0") // Miss는 0 이상이어야 합니다
    long miss,

    @NotNull(message = "state is required") // state
    String state
) {}