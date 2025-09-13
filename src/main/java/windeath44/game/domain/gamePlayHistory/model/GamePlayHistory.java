package windeath44.game.domain.gamePlayHistory.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import windeath44.game.domain.gamePlayHistory.model.type.GamePlayHistoryState;

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
    
    private String userId;
    private Long musicId;
    private float completionRate;
    private long combo;
    private long perfectPlus;
    private long perfect;
    private long great;
    private long good;
    private long miss;
    @Column(name = "`rank`")
    private String rank;
    private GamePlayHistoryState state;

    @CreatedDate
    private LocalDateTime playedAt;
    
}
