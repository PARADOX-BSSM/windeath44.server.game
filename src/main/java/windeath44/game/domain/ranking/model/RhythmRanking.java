package windeath44.game.domain.ranking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;



@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "musicId"}))
public class RhythmRanking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rankingId;
    private String userId;
    private Long musicId;
    private float completionRate;

    @LastModifiedDate
    private LocalDateTime updatedAt;

}
