package windeath44.game.domain.player.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import windeath44.game.domain.player.dto.projection.PlayerRankingProjection;
import windeath44.game.domain.player.dto.response.PlayerResponse;
import windeath44.game.domain.player.mapper.PlayerMapper;
import windeath44.game.domain.player.model.Player;
import windeath44.game.domain.player.repository.PlayerRepository;
import windeath44.game.global.dto.CursorPage;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlayerQueryService {
    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    public PlayerResponse getPlayerRating(String playerId) {
        Player player = playerRepository.findByPlayerId(playerId)
                .orElseGet(() -> {
                    Float rating = playerRepository.calculatePlayerRatingFromRecentGames(playerId);
                    Player newPlayer = Player.builder()
                            .playerId(playerId)
                            .rating(rating != null ? rating : 0.0f)
                            .build();
                    return playerRepository.save(newPlayer);
                });
        PlayerResponse playerResponse = playerMapper.toResponse(player);
        return playerResponse;
    }

    public CursorPage<PlayerResponse> getAllPlayersRanking(Long cursorRanking, int size) {
        Slice<PlayerRankingProjection> playerSlice = playerRepository.findAllPlayersRankingWithCursor(
                cursorRanking,
                PageRequest.of(0, size)
        );

        List<PlayerResponse> playerResponse = playerMapper.toRankingResponseList(playerSlice);

        return CursorPage.from(size, playerResponse);
    }
}
