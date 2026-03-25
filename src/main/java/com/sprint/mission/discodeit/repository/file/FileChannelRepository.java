package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.io.*;
import java.util.*;

public class FileChannelRepository implements ChannelRepository {
    private final String directory;
    private final String filePath;
    private final Map<UUID, Channel> store;

    // 생성자에서 디렉터리 경로를 받아옴
    public FileChannelRepository(String directory) {
        this.directory = directory;
        this.filePath = directory + File.separator + "channel.db";
        this.store = load();
    }

    // 파일에서 데이터를 읽어오는 메서드
    @SuppressWarnings("Unchecked")
    private Map<UUID, Channel> load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (Map<UUID, Channel>) ois.readObject();
        } catch (FileNotFoundException | EOFException e) {
            return new HashMap<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();

        }
    }

    //메모리의 store를 파일로 저장하는 메서드
    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(store);
        } catch (IOException e) {
            e.printStackTrace();
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
    public void deleteById(UUID id) {
        store.remove(id);
        saveToFile();
    }

    @Override
    public List<Channel> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<Channel> findAllByIsPrivate(boolean isPrivate) {
        List<Channel> result = new ArrayList<>();
        for (Channel ch : store.values()) {
            if (ch.isPrivate() == isPrivate) result.add(ch);
        }
        return result;
    }

    @Override
    public List<Channel> findAllPrivateByUserId(UUID userId) {
        return new ArrayList<>(); // 실제 구현은 서비스에서 ReadStatus와 조합
    }

    @Override
    public void delete(UUID id) {
        store.remove(id);
        saveToFile();
    }
}
