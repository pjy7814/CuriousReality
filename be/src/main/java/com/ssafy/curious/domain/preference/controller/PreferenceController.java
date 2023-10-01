package com.ssafy.curious.domain.preference.controller;

import com.ssafy.curious.domain.preference.dto.SaveHistoryRequest;
import com.ssafy.curious.domain.preference.service.HistoryService;
import com.ssafy.curious.security.dto.UserAuth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
public class PreferenceController {

    private final HistoryService historyService;

    /**
     * 특정 기사 클릭 시 시청 기록에 저장
     */
    @PostMapping("/history")
    public ResponseEntity<Void> saveHistory(@AuthenticationPrincipal UserAuth userAuth,
                                            @RequestBody SaveHistoryRequest request) {
        historyService.saveHistory(userAuth, request);
        return ResponseEntity.ok().body(null);
    }
}
