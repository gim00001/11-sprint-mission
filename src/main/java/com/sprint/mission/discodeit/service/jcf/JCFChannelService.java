package com.sprint.mission.discodit.service.jcf;

import com.sprint.mission.discodit.entity.Channel;
import com.sprint.mission.discodit.service.ChannelService;

import java.util.*;

public class JCFChannelService implements ChannelService{
     //JCF (예: HasMap, ArrayList 등)로 데이터 보관 & 메소드 구현
    private final Map<UUID, Channel> data;

    public JCFChannelService() {
        this.data = new HashMap<>();
    }
    @Override
    public Channel createChannel(String name, String description) {
        Channel channel = new Channel(name, description);
        data.put(channel.getId(), channel);
        return channel;
    }
    @Override
    public Channel getChannel(UUID id) {
        return data.get(id);
    }

    @Override
    public List<Channel> getAllChannels() {
        return new ArrayList<>(data.values());
    }

    @Override
    public void updateChannel(UUID id, String name, String description) {
        Channel channel = data.get(id);
        if (channel != null) {
            channel.update(name, description);
        }
    }

    @Override
    public void deleteChannel(UUID id) {
        data.remove(id);
        }
    }

