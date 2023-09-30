package com.ssafy.curious.domain.auth.dto;

import lombok.Builder;

@Builder
public class Preference {
    @Builder.Default
    private Boolean politics = false;
    @Builder.Default
    private Boolean economics = false;
    @Builder.Default
    private Boolean social = false;
    @Builder.Default
    private Boolean science = false;
    @Builder.Default
    private Boolean world = false;


}