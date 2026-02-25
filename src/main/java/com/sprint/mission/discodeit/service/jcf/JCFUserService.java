package com.sprint.mission.discodit.service.jcf;

import com.sprint.mission.discodit.entity.User;
import com.sprint.mission.discodit.service.UserService;

import java.util.*;

public class JCFUserService implements UserService {
    // JCF(예: HasMap, ArrayList 등)로 데이터 보관 & 메소드 구현

    // 데이터를 저장할 HashMap 컬렉션을 final로 선언하고 생성자에서 초기화!
    // 메모리 저장소
    private final Map<UUID, User> userStore = new HashMap<>();

    // User 생성 Create
    @Override
    public User createUser(String name, String email) {
        User user = new User(name, email);
        userStore.put(user.getId(), user);
        return user;
    }

    //READ (1)
    @Override
    public User getUser(UUID id) {
         return userStore.get(id);
    }

    // READ(ALL)
    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(userStore.values());
    }

    //UPDATE
    @Override
    public void updateUser(UUID id, String name, String email) {
        User user = userStore.get(id);
        if (user != null) {
            user.update(name, email); //  User 엔티티에 update 메서드 참조
        }
    }
    // DELETE
    @Override
    public void deleteUser(UUID id) {
        userStore.remove(id);
        }
    }




