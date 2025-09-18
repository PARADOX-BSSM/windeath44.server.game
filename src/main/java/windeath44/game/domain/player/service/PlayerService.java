package windeath44.game.domain.player.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import windeath44.game.domain.rhythmGamePlayHistory.event.RhythmGamePlayHistorySavedEvent;
import windeath44.game.domain.player.dto.response.PlayerResponse;
import windeath44.game.domain.player.mapper.PlayerMapper;
import windeath44.game.domain.player.model.Player;
import windeath44.game.domain.player.repository.PlayerRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    @Async
    @EventListener
    @Transactional
    public void handleRhythmGamePlayHistorySaved(RhythmGamePlayHistorySavedEvent event) {
        log.info("Processing player rating update for userId: {}", event.userId());

        playerRepository.findByPlayerId(event.userId())
                .ifPresentOrElse(
                    player -> updatePlayerRating(player),
                    () -> createNewPlayer(event.userId())
                );
    }

    private void updatePlayerRating(Player player) {
        Float newRating = playerRepository.calculatePlayerRatingFromRecentGames(player.getPlayerId());
        player.setRating(newRating != null ? newRating : 0.0f);
        playerRepository.save(player);
        log.info("Updated player rating for userId: {} with new rating: {}",
                player.getPlayerId(), player.getRating());
    }

    private void createNewPlayer(String userId) {
        Float rating = playerRepository.calculatePlayerRatingFromRecentGames(userId);
        Player newPlayer = Player.builder()
                .playerId(userId)
                .rating(rating != null ? rating : 0.0f)
                .build();
        playerRepository.save(newPlayer);
        log.info("Created new player for userId: {} with rating: {}",
                userId, newPlayer.getRating());
    }

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
        return playerMapper.toResponse(player);
    }
}