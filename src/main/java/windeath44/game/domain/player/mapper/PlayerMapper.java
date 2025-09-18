package windeath44.game.domain.player.mapper;

import org.springframework.stereotype.Component;
import windeath44.game.domain.player.dto.response.PlayerResponse;
import windeath44.game.domain.player.model.Player;

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
}