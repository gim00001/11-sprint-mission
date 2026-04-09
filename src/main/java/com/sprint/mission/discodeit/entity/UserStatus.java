package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserStatus extends BaseUpdatableEntity {

  private User user;
  private Instant lastActiveAt;

  public UserStatus(User user, Instant lastActiveAt) {
    this.user = user;
    this.lastActiveAt = lastActiveAt;
  }

  public boolean isOnline() {
    return lastActiveAt != null &&
        lastActiveAt.isAfter(Instant.now().minusSeconds(300));
  }

  public void update(Instant lastActiveAt) {
    this.lastActiveAt = lastActiveAt;
  }
}
