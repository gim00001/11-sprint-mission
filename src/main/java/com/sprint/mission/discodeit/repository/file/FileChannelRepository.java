package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.io.*;
import java.util.*;

public class FileChannelRepository implements ChannelRepository {
    private static final String FILE_PATH = "channel.db";

    //Channel 데이터를 메모리에서 관리하는 맵
    private Map<UUID, Channel> store = load();

    // 파일에서 데이터를 읽어오는 메서드
    @SuppressWarnings("unchecked")
    private Map<UUID, Channel> load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            return (Map<UUID, Channel>) ois.readObject();
        } catch (FileNotFoundException | EOFException e) {
            //  파일이 없거나 완전히 비어 있으면 빈 Map 반환
            return new HashMap<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    //메모리의 store를 파일로 저장하는 메서드
    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(store);
        } catch (IOException e) {
            throw new RuntimeException(e);
            }
        }

    @Override
    public Channel save(Channel channel) {
            store.put(channel.getId(), channel);
            saveToFile();
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
    public List<Channel> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void delete(UUID id) {
        store.remove(id);
        saveToFile();
    }
}
