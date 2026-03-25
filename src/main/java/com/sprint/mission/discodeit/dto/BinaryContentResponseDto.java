package com.sprint.mission.discodeit.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class BinaryContentResponseDto {
    private UUID id;
    private String contentType;
    private UUID userId;
    private UUID messageId;

}
