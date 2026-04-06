package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.Channel;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ChannelCreateRequestDto {

  private String name;
  private String description;
  private Channel.ChannelType type; // PUBLIC, PRIVATE
  private List<UUID> participantUserIds; // 비공개 채널일 때만 값이 있음

}
