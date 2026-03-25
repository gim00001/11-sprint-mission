package com.sprint.mission.discodeit.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class MessageResponseDto {
    private UUID id;
    private UUID channelId;
    private UUID authorId;
    private String content;
    private Instant createdAt;
    private Instant updatedAt;
    private List<UUID> attachmentIds;
}
