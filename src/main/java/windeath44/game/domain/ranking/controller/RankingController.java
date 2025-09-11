package windeath44.game.domain.ranking.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import windeath44.game.domain.ranking.dto.RankingRequest;
import windeath44.game.domain.ranking.dto.RankingResponse;
import windeath44.game.domain.ranking.service.RankingService;
import windeath44.game.global.dto.CursorPage;
import windeath44.game.global.dto.ResponseDto;
import windeath44.game.global.util.HttpUtil;

@RestController
@RequiredArgsConstructor
@RequestMapping("/game/rankings")
public class RankingController {
    
    private final RankingService rankingService;
    
    @GetMapping
    public ResponseEntity<ResponseDto<CursorPage<RankingResponse>>> getRankings(
            @Valid @RequestBody RankingRequest request,
            @RequestParam(value="cursorId", required=false) Long cursorId,
            @RequestParam(value="size", defaultValue="10") int size
    ) {
        CursorPage<RankingResponse> rankings = rankingService.getRankingByMusicId(request);
        ResponseDto<CursorPage<RankingResponse>> responseDto = HttpUtil.success("", rankings);
        return ResponseEntity.ok(responseDto);
    }
}