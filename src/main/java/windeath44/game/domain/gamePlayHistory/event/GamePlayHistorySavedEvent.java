package windeath44.game.domain.gamePlayHistory.event;

public record GamePlayHistorySavedEvent(
        String userId,
        Long musicId,
        Float completionRate,
        Long combo,
        Long perfectPlus,
        Long perfect,
        Long great,
        Long good,
        Long miss
) {
    public static GamePlayHistorySavedEvent from(String userId, Long musicId, Float completionRate,
                                                 Long combo, Long perfectPlus, Long perfect, 
                                                 Long great, Long good, Long miss) {
        return new GamePlayHistorySavedEvent(userId, musicId, completionRate, combo,
                                           perfectPlus, perfect, great, good, miss);
    }
}