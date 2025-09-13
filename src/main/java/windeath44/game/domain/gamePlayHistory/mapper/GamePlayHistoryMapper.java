package windeath44.game.domain.gamePlayHistory.mapper;

import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import windeath44.game.domain.gamePlayHistory.dto.request.GamePlayHistoryRequest;
import windeath44.game.domain.gamePlayHistory.dto.response.GamePlayHistoryResponse;
import windeath44.game.domain.gamePlayHistory.model.GamePlayHistory;
import windeath44.game.domain.gamePlayHistory.model.type.GamePlayHistoryState;
import windeath44.game.domain.gamePlayHistory.util.RankCalculator;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GamePlayHistoryMapper {
    
    public GamePlayHistory toEntity(GamePlayHistoryRequest request, String userId) {
        String rank = RankCalculator.calculateRank(request.getCompletionRate());
        GamePlayHistoryState state = (request.getState() == null || request.getState().trim().isEmpty()) 
            ? null 
            : GamePlayHistoryState.valueOf(request.getState());

        return GamePlayHistory.builder()
                .userId(userId)
                .musicId(request.getMusicId())
                .completionRate(request.getCompletionRate())
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
    
    public GamePlayHistoryResponse toResponse(GamePlayHistory entity) {
        return GamePlayHistoryResponse.builder()
                .gamePlayHistoryId(entity.getGamePlayHistoryId())
                .userId(entity.getUserId())
                .musicId(entity.getMusicId())
                .completionRate(entity.getCompletionRate())
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
    
    public List<GamePlayHistoryResponse> toResponseList(Slice<GamePlayHistory> gamePlayHistorySlice) {
        return gamePlayHistorySlice
                .getContent()
                .stream()
                .map(this::toResponse)
                .toList();
    }
    
    public List<GamePlayHistoryResponse> toResponseList(List<GamePlayHistory> gamePlayHistoryList) {
        return gamePlayHistoryList.stream()
                .map(this::toResponse)
                .toList();
    }
}