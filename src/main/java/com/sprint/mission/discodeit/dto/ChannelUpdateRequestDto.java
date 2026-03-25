package com.sprint.mission.discodeit.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class ChannelUpdateRequestDto {
    private UUID id;
    private String name;
    private String description;
}
