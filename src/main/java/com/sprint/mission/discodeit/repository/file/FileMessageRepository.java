package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.io.*;
import java.time.Instant;
import java.util.*;

public class FileMessageRepository implements MessageRepository {
    private final String directory;
    private final String filePath;
    private final Map<UUID, Message> store;

    public FileMessageRepository(String directory) {
        this.directory = directory;
        this.filePath = directory + File.separator + "message.db";
        this.store = load();
    }

    @SuppressWarnings("unchecked")
    private Map<UUID, Message> load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (Map<UUID, Message>) ois.readObject();
        } catch (FileNotFoundException | EOFException e) {
            return new HashMap<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    // 메모리의 store를 파일로 저장하는 메서드
    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(store);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Message save(Message message) {
        store.put(message.getId(), message);
        saveToFile();
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
        saveToFile();
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
                .filter(m -> m.getChannelId().equals(channelId))
                .map(Message::getCreatedAt)
                .max(Comparator.naturalOrder());
    }

    @Override
    public void deleteAllByChannelId(UUID channelId) {
        store.entrySet().removeIf(entry -> entry.getValue().getChannelId().equals(channelId));
        saveToFile();
    }

    @Override
    public void deleteById(UUID id) {
        store.remove(id);
        saveToFile();
    }

}