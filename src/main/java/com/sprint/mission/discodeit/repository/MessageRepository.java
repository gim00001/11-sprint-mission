package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Message;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageRepository {
    Message save(Message message);

    Optional<Message> findById(UUID id);

    Optional<Instant> findLatestCreatedAtByChannelId(UUID channelId);

    List<Message> findAll();

    List<Message> findByAuthorId(UUID authorId);

    List<Message> findByChannelId(UUID channelId);

    void delete(UUID id);

    void deleteAllByChannelId(UUID channelId);

    void deleteById(UUID id);
}
