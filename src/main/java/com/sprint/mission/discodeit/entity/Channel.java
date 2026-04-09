package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Channel extends BaseUpdatableEntity {

  private ChannelType type;
  private String name;
  private String description;

  // PUBLIC 채널 생성
  public Channel(ChannelType type, String name, String description) {
    this.type = type;
    this.name = name;
    this.description = description;
  }

  public void update(String newName, String newDescription) {
    if (newName != null && !newName.isEmpty()) {
      this.name = newName;
    }
    if (newDescription != null && !newDescription.isEmpty()) {
      this.description = newDescription;
    }
  }

  public enum ChannelType {
    PUBLIC,
    PRIVATE
  }
}
