package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageService {
    Message create(String content, UUID channelId, UUID authorId);
    Optional<Message> findById(UUID id);
    List<Message> findAll();
    void delete(UUID id);
    List<Message> findByChannelId(UUID channelId);
    List<Message> findByAuthorId(UUID authorId);
}
