package windeath44.game.domain.rhythmGamePlayHistory.event;

public record RhythmGamePlayHistorySavedEvent(
        String userId,
        Long musicId,
        Float completionRate,
        Float rating,
        Long combo,
        Long perfectPlus,
        Long perfect,
        Long great,
        Long good,
        Long miss
) {
    public static RhythmGamePlayHistorySavedEvent from(String userId, Long musicId, Float completionRate,
                                                 Float rating, Long combo, Long perfectPlus, Long perfect,
                                                 Long great, Long good, Long miss) {
        return new RhythmGamePlayHistorySavedEvent(userId, musicId, completionRate, rating, combo,
                                           perfectPlus, perfect, great, good, miss);
    }
}