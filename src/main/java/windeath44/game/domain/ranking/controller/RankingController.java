package windeath44.game.domain.ranking.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import windeath44.game.domain.ranking.dto.response.RankingResponse;
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
            @RequestParam(value="musicId") Long musicId,
            @RequestParam(value="cursorRank", required=false) Long cursorRank,
            @RequestParam(value="size", defaultValue="10") int size
    ) {
        CursorPage<RankingResponse> rankings = rankingService.getRankingByMusicId(musicId, cursorRank, size);
        ResponseDto<CursorPage<RankingResponse>> responseDto = HttpUtil.success("", rankings);
        return ResponseEntity.ok(responseDto);
    }
}