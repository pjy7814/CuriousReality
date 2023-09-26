package com.ssafy.curious.domain.preference.controller;

import com.ssafy.curious.domain.preference.dto.SaveHistoryRequest;
import com.ssafy.curious.domain.preference.service.HistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PreferenceController {

    private final HistoryService historyService;

    /**
     * 특정 기사 클릭 시 시청 기록에 저장
     */
    // Todo: JWT 적용 후 @AuthenticationPrincipal로 변경
    @PostMapping("history/{memberId}")
    public ResponseEntity<Void> saveHistory(@PathVariable("member_id") Long memberId,
                                            @RequestBody SaveHistoryRequest request) {
        historyService.saveHistory(memberId, request);
        return ResponseEntity.ok().body(null);
    }

}
