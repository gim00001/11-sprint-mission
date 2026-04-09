package com.sprint.mission.discodeit.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.time.Instant;
import lombok.Getter;
import org.springframework.data.annotation.LastModifiedBy;

@Getter
@MappedSuperclass
public abstract class BaseUpdateEntity extends BaseEntity {

  @LastModifiedBy
  @Column(nullable = false)
  private Instant updatedAt;
}
