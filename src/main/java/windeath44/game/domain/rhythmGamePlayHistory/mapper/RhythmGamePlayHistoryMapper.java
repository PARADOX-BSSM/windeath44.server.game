package windeath44.game.domain.rhythmGamePlayHistory.mapper;

import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import windeath44.game.domain.rhythmGamePlayHistory.dto.request.RhythmGamePlayHistoryRequest;
import windeath44.game.domain.rhythmGamePlayHistory.dto.response.RhythmGamePlayHistoryResponse;
import windeath44.game.domain.rhythmGamePlayHistory.model.RhythmGamePlayHistory;
import windeath44.game.domain.rhythmGamePlayHistory.model.type.RhythmGamePlayHistoryState;
import windeath44.game.domain.rhythmGamePlayHistory.util.RankCalculator;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RhythmGamePlayHistoryMapper {
    
    public RhythmGamePlayHistory toEntity(RhythmGamePlayHistoryRequest request, String userId) {
        String rank = RankCalculator.calculateRank(request.getCompletionRate());
        RhythmGamePlayHistoryState state = (request.getState() == null || request.getState().trim().isEmpty())
            ? null
            : RhythmGamePlayHistoryState.valueOf(request.getState());

        return RhythmGamePlayHistory.builder()
                .userId(userId)
                .musicId(request.getMusicId())
                .completionRate(request.getCompletionRate())
                .rating(request.getRating())
                .combo(request.getCombo())
                .perfectPlus(request.getPerfectPlus())
                .perfect(request.getPerfect())
                .great(request.getGreat())
                .good(request.getGood())
                .miss(request.getMiss())
                .rank(rank)
                .state(state)
                .build();
    }
    
    public RhythmGamePlayHistoryResponse toResponse(RhythmGamePlayHistory entity) {
        return RhythmGamePlayHistoryResponse.builder()
                .gamePlayHistoryId(entity.getGamePlayHistoryId())
                .userId(entity.getUserId())
                .musicId(entity.getMusicId())
                .completionRate(entity.getCompletionRate())
                .rating(entity.getRating())
                .combo(entity.getCombo())
                .perfectPlus(entity.getPerfectPlus())
                .perfect(entity.getPerfect())
                .great(entity.getGreat())
                .good(entity.getGood())
                .miss(entity.getMiss())
                .rank(entity.getRank())
                .state(entity.getState())
                .playedAt(entity.getPlayedAt())
                .build();
    }
    
    public List<RhythmGamePlayHistoryResponse> toResponseList(Slice<RhythmGamePlayHistory> gamePlayHistorySlice) {
        return gamePlayHistorySlice
                .getContent()
                .stream()
                .map(this::toResponse)
                .toList();
    }
    
    public List<RhythmGamePlayHistoryResponse> toResponseList(List<RhythmGamePlayHistory> gamePlayHistoryList) {
        return gamePlayHistoryList.stream()
                .map(this::toResponse)
                .toList();
    }

    public RhythmGamePlayHistoryResponse toMergedResponse(Object[] aggregatedData) {
        if (aggregatedData == null || aggregatedData.length < 11) {
            throw new IllegalArgumentException("Invalid aggregated data");
        }

        float completionRate = ((Number) aggregatedData[0]).floatValue();
        float rating = ((Number) aggregatedData[1]).floatValue();
        long combo = ((Number) aggregatedData[2]).longValue();
        long perfectPlus = ((Number) aggregatedData[3]).longValue();
        long perfect = ((Number) aggregatedData[4]).longValue();
        long great = ((Number) aggregatedData[5]).longValue();
        long good = ((Number) aggregatedData[6]).longValue();
        long miss = ((Number) aggregatedData[7]).longValue();
        String stateString = (String) aggregatedData[8];
        String userId = (String) aggregatedData[9];
        Long musicId = ((Number) aggregatedData[10]).longValue();

        RhythmGamePlayHistoryState state = RhythmGamePlayHistoryState.valueOf(stateString);
        String rank = RankCalculator.calculateRank(completionRate);

        return RhythmGamePlayHistoryResponse.builder()
                .gamePlayHistoryId(null) // 병합된 가상 기록이므로 null
                .userId(userId)
                .musicId(musicId)
                .completionRate(completionRate)
                .rating(rating)
                .combo(combo)
                .perfectPlus(perfectPlus)
                .perfect(perfect)
                .great(great)
                .good(good)
                .miss(miss)
                .rank(rank)
                .state(state)
                .playedAt(null) // 병합된 기록이므로 null
                .build();
    }

    public List<RhythmGamePlayHistoryResponse> toMergedResponseList(List<Object[]> aggregatedDataList) {
        return aggregatedDataList.stream()
                .map(this::toMergedResponseFromList)
                .toList();
    }

    private RhythmGamePlayHistoryResponse toMergedResponseFromList(Object[] aggregatedData) {
        if (aggregatedData == null || aggregatedData.length < 10) {
            throw new IllegalArgumentException("Invalid aggregated data for list response");
        }

        Long musicId = ((Number) aggregatedData[0]).longValue();
        float completionRate = ((Number) aggregatedData[1]).floatValue();
        float rating = ((Number) aggregatedData[2]).floatValue();
        long combo = ((Number) aggregatedData[3]).longValue();
        long perfectPlus = ((Number) aggregatedData[4]).longValue();
        long perfect = ((Number) aggregatedData[5]).longValue();
        long great = ((Number) aggregatedData[6]).longValue();
        long good = ((Number) aggregatedData[7]).longValue();
        long miss = ((Number) aggregatedData[8]).longValue();
        String stateString = (String) aggregatedData[9];
        String userId = (String) aggregatedData[10];

        RhythmGamePlayHistoryState state = RhythmGamePlayHistoryState.valueOf(stateString);
        String rank = RankCalculator.calculateRank(completionRate);

        return RhythmGamePlayHistoryResponse.builder()
                .gamePlayHistoryId(null) // 병합된 가상 기록이므로 null
                .userId(userId)
                .musicId(musicId)
                .completionRate(completionRate)
                .rating(rating)
                .combo(combo)
                .perfectPlus(perfectPlus)
                .perfect(perfect)
                .great(great)
                .good(good)
                .miss(miss)
                .rank(rank)
                .state(state)
                .playedAt(null) // 병합된 기록이므로 null
                .build();
    }
}