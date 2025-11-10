package windeath44.game.domain.rhythmGamePlayHistory.model.type;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import windeath44.game.domain.rhythmGamePlayHistory.util.RankCalculator;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public enum Rank {
    SSS_PLUS("SSS+", 4), //SSS+
    SSS("SSS", 3),
    SS_PLUS("SS+", 2.5F), //SS+
    SS("SS", 2),
    S_PLUS("S+", 1.5F),
    S("S",1.25F),
    AAA("AAA", 1),
    AA("AA", 1),
    A("A", 1),
    BBB("BBB", 1),
    BB("BB", 1),
    B("B", 1),
    C("C", 1),
    D("D", 1)
    ;

    private final String displayName;
    private final float rankCoefficient;
    private static final Map<String, Rank> rankMap = Arrays.stream(values())
            .collect(Collectors.toMap(Rank::getDisplayName, Function.identity()));


    public static Rank calculate(float completionRate) {
        return Rank.fromString(
                RankCalculator.calculateRank(completionRate)
        );
    }

    private static Rank fromString(String rank) {
        return rankMap.get(rank);
    }
}
