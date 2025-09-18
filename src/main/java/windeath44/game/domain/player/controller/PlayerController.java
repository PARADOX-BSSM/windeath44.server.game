package windeath44.game.domain.player.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import windeath44.game.domain.player.dto.response.PlayerResponse;
import windeath44.game.domain.player.service.PlayerQueryService;
import windeath44.game.global.dto.ResponseDto;
import windeath44.game.global.util.HttpUtil;

@RestController
@RequestMapping("/game/players")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerQueryService playerQueryService;

    @GetMapping("/{playerId}/rating")
    public ResponseEntity<ResponseDto<PlayerResponse>> getPlayerRating(
            @PathVariable("playerId") String playerId) {
        PlayerResponse player = playerQueryService.getPlayerRating(playerId);
        ResponseDto<PlayerResponse> responseDto = HttpUtil.success("player rating successfully retrieved", player);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/my/rating")
    public ResponseEntity<ResponseDto<PlayerResponse>> getMyRating(
            @RequestHeader("user-id") String userId) {
        PlayerResponse player = playerQueryService.getPlayerRating(userId);
        ResponseDto<PlayerResponse> responseDto = HttpUtil.success("my rating successfully retrieved", player);
        return ResponseEntity.ok(responseDto);
    }
}