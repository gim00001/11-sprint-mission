package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.util.*;

public class JCFChannelRepository implements ChannelRepository {
    //Channel 데이터를 메모리에서 관리하는 맵
    private final Map<UUID, Channel> store = new HashMap<>();

    @Override
    public Channel save(Channel channel) {
        store.put(channel.getId(), channel);
        return channel;
    }

    @Override
    public Optional<Channel> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Channel> findByName(String name) {
        return store.values().stream()
                .filter(channel -> channel.getName().equals(name))
                .findFirst();
    }

    @Override
    public List<Channel> findAllByIsPrivate(boolean isPrivate) {
        List<Channel> result = new ArrayList<>();
        for (Channel ch : store.values()) {
            if (ch.isPrivate() == isPrivate) {
                result.add(ch);
            }
        }
        return result;
    }

    @Override
    public List<Channel> findAllPrivateByUserId(UUID userId) {
        // 실제 시스템에서는 ReadStatus 등에서 참여자 정보를 얻어야 하며
        // 간단 예시로는 PRIVATE 채널 전체를 반환하거나, 서비스에서 조합
        List<Channel> result = new ArrayList<>();
        for (Channel ch : store.values()) {
            if (ch.isPrivate()) {
                result.add(ch); // 실제 구현에 맞게 수정 필요
            }
        }
        return result;
    }

    @Override
    public List<Channel> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void delete(UUID id) {

        store.remove(id);
    }

    @Override
    public void deleteById(UUID id) {
        store.remove(id);
    }
}
