package com.sprint.mission.discodeit.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class UserStatusUpdateByUserIdRequestDto {
    private UUID userId;
    private Instant lastAccessAt;
}
