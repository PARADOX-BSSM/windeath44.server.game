package windeath44.game.domain.gamePlayHistory.controller;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import windeath44.game.domain.gamePlayHistory.dto.request.GamePlayHistoryRequest;
import windeath44.game.domain.gamePlayHistory.dto.response.GamePlayHistoryResponse;
import windeath44.game.domain.gamePlayHistory.service.GamePlayHistoryService;
import windeath44.game.global.dto.CursorPage;
import windeath44.game.global.dto.ResponseDto;
import windeath44.game.global.util.HttpUtil;

@RestController
@RequestMapping("/game/game-play-history")
@RequiredArgsConstructor
public class GamePlayHistoryController {
    private final GamePlayHistoryService gamePlayHistoryService;

    @PostMapping
    public ResponseEntity<ResponseDto<GamePlayHistoryResponse>> saveGamePlayHistory(
            @RequestHeader("user-id") Long userId,
            @Valid @RequestBody GamePlayHistoryRequest request) {
        gamePlayHistoryService.saveGamePlayHistory(request, userId);
        ResponseDto<GamePlayHistoryResponse> responseDto = HttpUtil.success("game play history saved successfully");
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{gamePlayHistoryId}")
    public ResponseEntity<ResponseDto<Void>> deleteGamePlayHistory(
            @PathVariable("gamePlayHistoryId") Long gamePlayHistoryId) {
        gamePlayHistoryService.deleteGamePlayHistory(gamePlayHistoryId);
        ResponseDto<Void> responseDto = HttpUtil.success("game play history successfully deleted.");
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/my")
    public ResponseEntity<ResponseDto<CursorPage<GamePlayHistoryResponse>>> getMyGamePlayHistories(
            @RequestHeader("user-id") Long userId,
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "10") int size) {
        CursorPage<GamePlayHistoryResponse> response = gamePlayHistoryService.getMyGamePlayHistories(userId, cursor, size);
        ResponseDto<CursorPage<GamePlayHistoryResponse>> responseDto = HttpUtil.success("my game play history get", response);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<CursorPage<GamePlayHistoryResponse>>> getAllGamePlayHistories(
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "10") int size) {
        CursorPage<GamePlayHistoryResponse> response = gamePlayHistoryService.getAllGamePlayHistories(cursor, size);
        ResponseDto<CursorPage<GamePlayHistoryResponse>> responseDto = HttpUtil.success("game play histories successfully get", response);
        return ResponseEntity.ok(responseDto);
    }
}