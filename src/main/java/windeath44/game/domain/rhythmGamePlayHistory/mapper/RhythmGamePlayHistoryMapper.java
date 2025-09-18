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
}