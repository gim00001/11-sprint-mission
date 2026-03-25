package com.sprint.mission.discodeit.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ReadStatusResponseDto {
    private UUID id;
    private UUID userId;
    private UUID channelId;
    private Instant lastReadAt;
}
