package windeath44.game.domain.rhythmGamePlayHistory.util;

import windeath44.game.domain.rhythmGamePlayHistory.model.type.Rank;

public class RhythmGameRatingCalculator {

    public static float calculate(float completionRate, int level, Rank rank) {
        // 레이팅 = 난이도 * 달성률 * 랭크 계수
        return completionRate * level * rank.getRankCoefficient();
    }
}
