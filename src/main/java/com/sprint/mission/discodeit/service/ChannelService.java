package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChannelService {
    // Channel 관련 서비스 메소드들 선언
    Channel create(String name, String description);
    Optional<Channel> findById(UUID id);
    Optional<Channel> findByName(String name);
    Iterable<Channel> findAll();
    void delete(UUID id);

}
