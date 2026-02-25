package com.sprint.mission.discodit.service;

import com.sprint.mission.discodit.entity.User;
import java.util.List;
import java.util.UUID;

public interface UserService {
    // User 관련 서비스 메소드들 선언
    // Create
    User createUser(String name, String email);

    // Read - 하나 조회
    User getUser(UUID id);

    // Read - 모두 조회
    List<User> getAllUsers();

    // Update
    void updateUser(UUID id, String name, String email);

    // Delete
    void deleteUser(UUID id);
}
