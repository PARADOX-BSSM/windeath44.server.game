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
@Transactional
public class PlayerCommandService {
    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    @Async
    @EventListener
    public void handleRhythmGamePlayHistorySaved(RhythmGamePlayHistorySavedEvent event) {
        log.info("Processing player rating update for userId: {}", event.userId());

        playerRepository.findByPlayerId(event.userId())
                .ifPresentOrElse(
                        this::updatePlayerRating,
                    () -> createNewPlayer(event.userId())
                );
    }

    private void updatePlayerRating(Player player) {
        Float newRating = playerRepository.calculatePlayerRatingFromRecentGames(player.getPlayerId());
        player.updateRating(newRating != null ? newRating : 0.0f);
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
}