package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.time.Instant;
import java.util.*;

public class JCFMessageRepository implements MessageRepository {
    //Message 데이터를 메모리에서 관리하는 맵
    private final Map<UUID, Message> store = new HashMap<>();

    @Override
    public Message save(Message message) {
        store.put(message.getId(), message);
        return message;
    }

    @Override
    public Optional<Message> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Message> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void delete(UUID id) {
        store.remove(id);
    }

    @Override
    public List<Message> findByAuthorId(UUID authorId) {
        List<Message> result = new ArrayList<>();
        for (Message message : store.values()) {
            if (message.getAuthorId().equals(authorId)) {
                result.add(message);
            }
        }
        return result;
    }

    @Override
    public List<Message> findByChannelId(UUID channelId) {
        List<Message> result = new ArrayList<>();
        for (Message message : store.values()) {
            if (message.getChannelId().equals(channelId)) {
                result.add(message);
            }
        }
        return result;
    }

    @Override
    public Optional<Instant> findLatestCreatedAtByChannelId(UUID channelId) {
        return store.values().stream()
                .filter(msg -> msg.getChannelId().equals(channelId))
                .map(Message::getCreatedAt)
                .max(Comparator.naturalOrder());
    }

    @Override
    public void deleteAllByChannelId(UUID channelId) {
        store.values().removeIf(msg -> msg.getChannelId().equals(channelId));
    }

    @Override
    public void deleteById(UUID id) {
        store.remove(id);
    }
}