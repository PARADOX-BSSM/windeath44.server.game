package windeath44.game.domain.rhythmGamePlayHistory.model.type;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import windeath44.game.domain.rhythmGamePlayHistory.util.RankCalculator;

import java.util.Map;

@RequiredArgsConstructor
@Getter
public enum Rank {
    SSSPlus(4), //SSS+
    SSS(3),
    SSPlus(2.5F), //SS+
    SS(2),
    SPlus(1.5F),
    S(1.25F),
    AAA(1),
    AA(1),
    A(1),
    BBB(1),
    BB(1),
    B(1),
    C(1),
    D(1)
    ;

    private final float rankCoefficient;

    public static Rank calculate(float completionRate) {
        return Rank.valueOf(
                RankCalculator.calculateRank(completionRate)
        );

    }
}
