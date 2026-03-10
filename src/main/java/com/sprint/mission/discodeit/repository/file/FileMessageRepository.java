package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.io.*;
import java.util.*;

public class FileMessageRepository implements MessageRepository {
    private static final String FILE_PATH = "message.db";

    private Map<UUID, Message> store = load();

    // 파일에서 데이터를 불러오는 메서드
    @SuppressWarnings("unchecked")
    private Map<UUID, Message> load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            return (Map<UUID, Message>) ois.readObject();
        } catch (FileNotFoundException  | EOFException e) {
            // 파일이 없거나 비어 있으면 빈 Map 반환
            return new HashMap<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    // 메모리의 store를 파일로 저장하는 메서드
    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
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

    }