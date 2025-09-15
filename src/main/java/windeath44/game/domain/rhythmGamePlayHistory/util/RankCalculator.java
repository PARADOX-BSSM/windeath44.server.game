package windeath44.game.domain.rhythmGamePlayHistory.util;

public class RankCalculator {
    
    public static String calculateRank(float completionRate) {
        if (completionRate >= 99.75) {
            return "SSS+";
        } else if (completionRate >= 99.5) {
            return "SSS";
        } else if (completionRate >= 99.25) {
            return "SS+";
        } else if (completionRate >= 99.0) {
            return "SS";
        } else if (completionRate >= 98.5) {
            return "S+";
        } else if (completionRate >= 98.0) {
            return "S";
        } else if (completionRate >= 97.0) {
            return "AAA";
        } else if (completionRate >= 96.0) {
            return "AA";
        } else if (completionRate >= 95.0) {
            return "A";
        } else if (completionRate >= 94.0) {
            return "BBB";
        } else if (completionRate >= 93.0) {
            return "BB";
        } else if (completionRate >= 92.0) {
            return "B";
        } else if (completionRate >= 90.0) {
            return "C";
        } else {
            return "D";
        }
    }
}