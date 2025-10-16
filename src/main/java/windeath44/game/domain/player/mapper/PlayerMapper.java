package windeath44.game.domain.player.mapper;

import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import windeath44.game.domain.player.dto.projection.PlayerRankingProjection;
import windeath44.game.domain.player.dto.response.PlayerResponse;
import windeath44.game.domain.player.model.Player;

import java.util.List;

@Component
public class PlayerMapper {

    public PlayerResponse toResponse(Player player) {
        return PlayerResponse.builder()
                .playerId(player.getPlayerId())
                .rating(player.getRating())
                .createdAt(player.getCreatedAt())
                .updatedAt(player.getUpdatedAt())
                .build();
    }

    public PlayerResponse toResponse(PlayerRankingProjection projection) {
        return PlayerResponse.builder()
                .playerId(projection.getPlayerId())
                .rating(projection.getRating())
                .ranking(projection.getRanking())
                .createdAt(projection.getCreatedAt())
                .updatedAt(projection.getUpdatedAt())
                .build();
    }

    public List<PlayerResponse> toResponseList(Slice<Player> playerSlice) {
        List<PlayerResponse> playerResponseList = playerSlice.getContent().stream()
                .map(this::toResponse)
                .toList();
        return playerResponseList;
    }

    public List<PlayerResponse> toRankingResponseList(Slice<PlayerRankingProjection> projectionSlice) {
        return projectionSlice.getContent().stream()
                .map(this::toResponse)
                .toList();
    }
}