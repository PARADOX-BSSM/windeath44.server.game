package windeath44.game.domain.rhythmGamePlayHistory.util;

import windeath44.game.domain.rhythmGamePlayHistory.model.type.Rank;

public class RhythmGameRatingCalculator {

    public static float calculate(float completionRate, int level, Rank rank) {
        // 레이팅 = (달성률 / 100) * 난이도 * 랭크 계수
        return (completionRate / 100) * level * rank.getRankCoefficient();
    }
}
