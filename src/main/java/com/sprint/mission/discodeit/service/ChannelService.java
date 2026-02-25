package com.sprint.mission.discodit.service;

import com.sprint.mission.discodit.entity.Channel;
import java.util.List;
import java.util.UUID;

public interface ChannelService {
    // Channel 관련 서비스 메소드들 선언
    Channel createChannel(String name, String description);
    Channel getChannel(UUID id);
    List<Channel> getAllChannels();
    void updateChannel(UUID id, String name, String description);
    void deleteChannel(UUID id);

}
