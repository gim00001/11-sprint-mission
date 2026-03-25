package com.sprint.mission.discodeit.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class UserStatusResponseDto {
    private UUID id;
    private UUID userId;
    private Instant lastAccessAt;
    private boolean online;
}
