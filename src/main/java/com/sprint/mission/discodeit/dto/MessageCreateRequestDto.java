package com.sprint.mission.discodeit.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class MessageCreateRequestDto {
    private UUID channelId;
    private UUID authorId;
    private String content;
    private List<BinaryContentCreateRequestDto> attachments;
}
