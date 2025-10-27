package windeath44.game.domain.rhythmGamePlayHistory.mapper;

import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import windeath44.game.domain.rhythmGamePlayHistory.dto.request.RhythmGamePlayHistoryRequest;
import windeath44.game.domain.rhythmGamePlayHistory.dto.response.BestRecordResponse;
import windeath44.game.domain.rhythmGamePlayHistory.dto.response.RhythmGamePlayHistoryResponse;
import windeath44.game.domain.rhythmGamePlayHistory.exception.InvalidGamePlayHistoryDataException;
import windeath44.game.domain.rhythmGamePlayHistory.model.RhythmGamePlayHistory;
import windeath44.game.domain.rhythmGamePlayHistory.model.type.Rank;
import windeath44.game.domain.rhythmGamePlayHistory.model.type.RhythmGamePlayHistoryState;
import windeath44.game.domain.rhythmGamePlayHistory.util.RankCalculator;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RhythmGamePlayHistoryMapper {
    
    public RhythmGamePlayHistory toEntity(RhythmGamePlayHistoryRequest request, float rating, Rank rank, String userId) {
        RhythmGamePlayHistoryState state = RhythmGamePlayHistoryState.valueOf(request.state());

        return RhythmGamePlayHistory.builder()
                .userId(userId)
                .musicId(request.musicId())
                .completionRate(request.completionRate())
                .rating(rating)
                .combo(request.combo())
                .perfectPlus(request.perfectPlus())
                .perfect(request.perfect())
                .great(request.great())
                .good(request.good())
                .miss(request.miss())
                .level(request.level())
                .rank(rank)
                .state(state)
                .rating(rating)
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
            throw new InvalidGamePlayHistoryDataException();
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

    public List<BestRecordResponse> toMergedResponseList(List<Object[]> aggregatedDataList) {
        return aggregatedDataList.stream()
                .map(this::toMergedResponseFromList)
                .toList();
    }

    private BestRecordResponse toMergedResponseFromList(Object[] aggregatedData) {
        if (aggregatedData == null || aggregatedData.length < 11) {
            throw new InvalidGamePlayHistoryDataException();
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
        String stateString = aggregatedData[9] != null ? aggregatedData[9].toString() : null;
        String userId = aggregatedData[10] != null ? aggregatedData[10].toString() : null;

        RhythmGamePlayHistoryState state = stateString != null ? RhythmGamePlayHistoryState.valueOf(stateString) : null;
        String rank = RankCalculator.calculateRank(completionRate);

        return BestRecordResponse.builder()
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
                .build();
    }
}