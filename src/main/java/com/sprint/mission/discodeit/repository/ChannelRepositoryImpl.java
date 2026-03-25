package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Channel;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Primary
public class ChannelRepositoryImpl implements ChannelRepository {
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
                .filter(ch -> name != null && name.equals(ch.getName()))
                .findFirst();
    }

    @Override
    public List<Channel> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<Channel> findAllByIsPrivate(boolean isPrivate) {
        List<Channel> result = new ArrayList<>();
        for (Channel ch : store.values()) {
            if (ch.isPrivate() == isPrivate)
                result.add(ch);
        }
        return result;
    }

    @Override
    public List<Channel> findAllPrivateByUserId(UUID userId) {
        List<Channel> result = new ArrayList<>();
        // 이 부분은 ReadStatusRepository의 help가 필요
        // 실제론 채널에 userId가 포함된 PRIVATE 채널만 반환해야 함.
        // 여기서는 해당 로직의 실제 구현이 ReadStatusRepository 내부 정보 필요
        return result;
        // 기본은 빈값(의미는 "메모리 레벨에서는 readStatusRepository 데이터를 서비스에 조합할것")
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
