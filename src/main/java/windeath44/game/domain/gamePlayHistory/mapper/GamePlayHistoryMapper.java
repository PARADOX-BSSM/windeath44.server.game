package windeath44.game.domain.gamePlayHistory.mapper;

import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import windeath44.game.domain.gamePlayHistory.dto.request.GamePlayHistoryRequest;
import windeath44.game.domain.gamePlayHistory.dto.response.GamePlayHistoryResponse;
import windeath44.game.domain.gamePlayHistory.model.GamePlayHistory;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GamePlayHistoryMapper {
    
    public GamePlayHistory toEntity(GamePlayHistoryRequest request, Long userId) {
        return GamePlayHistory.builder()
                .userId(userId)
                .musicId(request.getMusicId())
                .completionRate(request.getCompletionRate())
                .combo(request.getCombo())
                .perfectPlus(request.getPerfectPlus())
                .perfect(request.getPerfect())
                .great(request.getGreat())
                .miss(request.getMiss())
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
                .miss(entity.getMiss())
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