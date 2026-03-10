package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.io.*;
import java.util.*;

public class FileUserRepository implements UserRepository {
    private static final String FILE_PATH = "user.db";

    // User 데이터를 메모리상에서 관리하는 맵
    private Map<UUID, User> store = load();

    // 파일에서 데이터를 불러오는 메서드
    @SuppressWarnings("unchecked")
    private Map<UUID, User> load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            return (Map<UUID, User>) ois.readObject();
        } catch (FileNotFoundException | EOFException e) {
            return new HashMap<>();
        } catch (Exception e) {
            e.printStackTrace(); // 문제 원인 추적을 위해 로그를 남김
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
    public User save(User user) {
        store.put(user.getId(), user);
        saveToFile();
        return user;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return store.values().stream()
                .filter(user -> user.getName().equals(username))
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void delete(UUID id) {
         store.remove(id);
         saveToFile();
    }
}
