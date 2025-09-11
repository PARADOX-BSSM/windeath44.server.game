package windeath44.game.domain.gamePlayHistory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import windeath44.game.domain.gamePlayHistory.dto.request.GamePlayHistoryRequest;
import windeath44.game.domain.gamePlayHistory.dto.response.GamePlayHistoryResponse;
import windeath44.game.domain.gamePlayHistory.exception.NotFoundGamePlayHistoryException;
import windeath44.game.domain.gamePlayHistory.mapper.GamePlayHistoryMapper;
import windeath44.game.domain.gamePlayHistory.model.GamePlayHistory;
import windeath44.game.domain.gamePlayHistory.repository.GamePlayHistoryRepository;
import windeath44.game.global.dto.CursorPage;
import windeath44.game.global.error.exception.ErrorCode;
import windeath44.game.global.error.exception.GlobalException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GamePlayHistoryService {
    
    private final GamePlayHistoryRepository gamePlayHistoryRepository;
    private final GamePlayHistoryMapper gamePlayHistoryMapper;

    @Transactional
    public void saveGamePlayHistory(GamePlayHistoryRequest request, Long userId) {
        GamePlayHistory gamePlayHistory = gamePlayHistoryMapper.toEntity(request, userId);
        gamePlayHistoryRepository.save(gamePlayHistory);
    }

    @Transactional
    public void deleteGamePlayHistory(Long gamePlayHistoryId) {
        GamePlayHistory gamePlayHistory = gamePlayHistoryRepository.findById(gamePlayHistoryId)
                .orElseThrow(NotFoundGamePlayHistoryException::getInstance);
        gamePlayHistoryRepository.delete(gamePlayHistory);
    }
    
    public CursorPage<GamePlayHistoryResponse> getMyGamePlayHistories(Long userId, Long cursorId, int size) {
        Slice<GamePlayHistory> historieSlice = gamePlayHistoryRepository.findByUserIdWithCursor(
                userId, cursorId, PageRequest.of(0, size));
        List<GamePlayHistoryResponse> historyList = gamePlayHistoryMapper.toResponseList(historieSlice.getContent());
        return CursorPage.from(size, historyList);
    }

    public CursorPage<GamePlayHistoryResponse> getAllGamePlayHistories(Long cursorId, int size) {
        Slice<GamePlayHistory> historiesSlice = gamePlayHistoryRepository.findAllWithCursor(
                cursorId, PageRequest.of(0, size));
        List<GamePlayHistoryResponse> historyList = gamePlayHistoryMapper.toResponseList(historiesSlice);
        return CursorPage.from(size, historyList);
    }

}