package windeath44.game.domain.rhythmGamePlayHistory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import windeath44.game.domain.rhythmGamePlayHistory.dto.request.RhythmGamePlayHistoryRequest;
import windeath44.game.domain.rhythmGamePlayHistory.dto.response.BestRecordResponse;
import windeath44.game.domain.rhythmGamePlayHistory.dto.response.RhythmGamePlayHistoryResponse;
import windeath44.game.domain.rhythmGamePlayHistory.event.RhythmGamePlayHistorySavedEvent;
import windeath44.game.domain.rhythmGamePlayHistory.exception.NotFoundRhythmGamePlayHistoryException;
import windeath44.game.domain.rhythmGamePlayHistory.mapper.RhythmGamePlayHistoryMapper;
import windeath44.game.domain.rhythmGamePlayHistory.model.RhythmGamePlayHistory;
import windeath44.game.domain.rhythmGamePlayHistory.model.type.Rank;
import windeath44.game.domain.rhythmGamePlayHistory.repository.RhythmGamePlayHistoryRepository;
import windeath44.game.domain.rhythmGamePlayHistory.util.RhythmGameRatingCalculator;
import windeath44.game.global.dto.CursorPage;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RhythmGamePlayHistoryService {

    private final RhythmGamePlayHistoryRepository rhythmGamePlayHistoryRepository;
    private final RhythmGamePlayHistoryMapper rhythmGamePlayHistoryMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void saveRhythmGamePlayHistory(RhythmGamePlayHistoryRequest request, String userId) {
        Rank rank = Rank.calculate(request.completionRate());
        float rating = RhythmGameRatingCalculator.calculate(request.completionRate(), request.level(), rank);
        RhythmGamePlayHistory rhythmGamePlayHistory = rhythmGamePlayHistoryMapper.toEntity(request, rating, rank, userId);
        rhythmGamePlayHistoryRepository.save(rhythmGamePlayHistory);


        RhythmGamePlayHistorySavedEvent event = RhythmGamePlayHistorySavedEvent.from(
            userId,
            request.musicId(),
            request.completionRate(),
            rating,
            request.combo(),
            request.perfectPlus(),
            request.perfect(),
            request.great(),
            request.good(),
            request.miss()
        );
        
        eventPublisher.publishEvent(event);
    }

    @Transactional
    public void deleteRhythmGamePlayHistory(Long gamePlayHistoryId) {
        RhythmGamePlayHistory rhythmGamePlayHistory = rhythmGamePlayHistoryRepository.findById(gamePlayHistoryId)
                .orElseThrow(NotFoundRhythmGamePlayHistoryException::getInstance);
        rhythmGamePlayHistoryRepository.delete(rhythmGamePlayHistory);
    }
    
    public CursorPage<RhythmGamePlayHistoryResponse> getMyRhythmGamePlayHistories(String userId, Long cursorId, int size) {
        Slice<RhythmGamePlayHistory> historieSlice = rhythmGamePlayHistoryRepository.findByUserIdWithCursor(
                String.valueOf(userId), cursorId, PageRequest.of(0, size));
        List<RhythmGamePlayHistoryResponse> historyList = rhythmGamePlayHistoryMapper.toResponseList(historieSlice.getContent());
        return CursorPage.from(size, historyList);
    }

    public CursorPage<RhythmGamePlayHistoryResponse> getAllRhythmGamePlayHistories(Long cursorId, int size) {
        Slice<RhythmGamePlayHistory> historiesSlice = rhythmGamePlayHistoryRepository.findAllWithCursor(
                cursorId, PageRequest.of(0, size));
        List<RhythmGamePlayHistoryResponse> historyList = rhythmGamePlayHistoryMapper.toResponseList(historiesSlice);
        return CursorPage.from(size, historyList);
    }

    public RhythmGamePlayHistoryResponse getBestRecord(String userId, Long musicId) {
        Object[] aggregatedData = rhythmGamePlayHistoryRepository.findBestMergedRecordByUserIdAndMusicId(userId, musicId)
                .orElseThrow(NotFoundRhythmGamePlayHistoryException::getInstance);

        return rhythmGamePlayHistoryMapper.toMergedResponse(aggregatedData);
    }

    public CursorPage<BestRecordResponse> getMyBestRecords(String userId, Long cursorMusicId, int size) {
        List<Object[]> bestRecords = rhythmGamePlayHistoryRepository.findMyBestRecordsWithCursor(userId, cursorMusicId, size + 1);
        List<BestRecordResponse> responseList = rhythmGamePlayHistoryMapper.toMergedResponseList(bestRecords);
        return CursorPage.from(size, responseList);
    }

}