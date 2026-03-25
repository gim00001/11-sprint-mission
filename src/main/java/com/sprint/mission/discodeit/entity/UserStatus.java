package com.sprint.mission.discodeit.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class UserStatus {
    private UUID id;
    private Instant createdAt;
    private Instant updatedAt;

    private UUID userId;
    private Instant lastAccessAt;

    public UserStatus(UUID userId, Instant lastAccessAt) {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
        this.userId = userId;
        this.lastAccessAt = lastAccessAt;
    }

    public boolean isOnline() {
        return lastAccessAt != null &&
                lastAccessAt.isAfter(Instant.now().minusSeconds(300));
    }
}
