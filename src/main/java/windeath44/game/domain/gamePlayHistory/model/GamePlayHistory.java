package windeath44.game.domain.gamePlayHistory.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
public class GamePlayHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gamePlayHistoryId;
    
    private Long userId; // 사용자 ID
    private Long musicId;
    private float completionRate;
    private long combo;
    private long perfectPlus;
    private long perfect;
    private long great;
    private long miss;
    
    @CreatedDate
    private LocalDateTime playedAt;
    
}
