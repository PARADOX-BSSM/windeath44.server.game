package windeath44.game.domain.rhythmGamePlayHistory.controller;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import windeath44.game.domain.rhythmGamePlayHistory.dto.request.RhythmGamePlayHistoryRequest;
import windeath44.game.domain.rhythmGamePlayHistory.dto.response.RhythmGamePlayHistoryResponse;
import windeath44.game.domain.rhythmGamePlayHistory.service.RhythmGamePlayHistoryService;
import windeath44.game.global.dto.CursorPage;
import windeath44.game.global.dto.ResponseDto;
import windeath44.game.global.util.HttpUtil;

@RestController
@RequestMapping("/game/rhythm-game-play-history")
@RequiredArgsConstructor
public class RhythmGamePlayHistoryController {
    private final RhythmGamePlayHistoryService rhythmGamePlayHistoryService;

    @PostMapping
    public ResponseEntity<ResponseDto<RhythmGamePlayHistoryResponse>> saveRhythmGamePlayHistory(
            @RequestHeader("user-id") String userId,
            @Valid @RequestBody RhythmGamePlayHistoryRequest request) {
        rhythmGamePlayHistoryService.saveRhythmGamePlayHistory(request, userId);
        ResponseDto<RhythmGamePlayHistoryResponse> responseDto = HttpUtil.success("rhythm game play history saved successfully");
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{gamePlayHistoryId}")
    public ResponseEntity<ResponseDto<Void>> deleteRhythmGamePlayHistory(
            @PathVariable("gamePlayHistoryId") Long gamePlayHistoryId) {
        rhythmGamePlayHistoryService.deleteRhythmGamePlayHistory(gamePlayHistoryId);
        ResponseDto<Void> responseDto = HttpUtil.success("rhythm game play history successfully deleted.");
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/my")
    public ResponseEntity<ResponseDto<CursorPage<RhythmGamePlayHistoryResponse>>> getMyRhythmGamePlayHistories(
            @RequestHeader("user-id") String userId,
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "10") int size) {
        CursorPage<RhythmGamePlayHistoryResponse> response = rhythmGamePlayHistoryService.getMyRhythmGamePlayHistories(userId, cursor, size);
        ResponseDto<CursorPage<RhythmGamePlayHistoryResponse>> responseDto = HttpUtil.success("my rhythm game play history get", response);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<CursorPage<RhythmGamePlayHistoryResponse>>> getAllRhythmGamePlayHistories(
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "10") int size) {
        CursorPage<RhythmGamePlayHistoryResponse> response = rhythmGamePlayHistoryService.getAllRhythmGamePlayHistories(cursor, size);
        ResponseDto<CursorPage<RhythmGamePlayHistoryResponse>> responseDto = HttpUtil.success("rhythm game play histories successfully get", response);
        return ResponseEntity.ok(responseDto);
    }
}