package windeath44.game.domain.rhythmGamePlayHistory.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RhythmGamePlayHistoryRequest {
    
    @NotNull(message = "Music ID is required") // 음악 ID는 필수입니다
    private Long musicId;
    
    @DecimalMin(value = "0.0", message = "Completion rate must be at least 0%") // 완주율은 0% 이상이어야 합니다
    @DecimalMax(value = "100.0", message = "Completion rate cannot exceed 100%") // 완주율은 100%를 초과할 수 없습니다
    private float completionRate;

    @DecimalMin(value = "0.0", message = "Rating must be at least 0") // 레이팅은 0 이상이어야 합니다
    private float rating;
    
    @Min(value = 0, message = "Combo must be at least 0") // 콤보는 0 이상이어야 합니다
    private long combo;
    
    @Min(value = 0, message = "Perfect Plus must be at least 0") // Perfect Plus는 0 이상이어야 합니다
    private long perfectPlus;
    
    @Min(value = 0, message = "Perfect must be at least 0") // Perfect는 0 이상이어야 합니다
    private long perfect;
    
    @Min(value = 0, message = "Great must be at least 0") // Great는 0 이상이어야 합니다
    private long great;
    
    @Min(value = 0, message = "Good must be at least 0") // Good는 0 이상이어야 합니다
    private long good;
    
    @Min(value = 0, message = "Miss must be at least 0") // Miss는 0 이상이어야 합니다
    private long miss;

    @NotNull(message = "state is required") // state
    private String state;
}