package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
public class User implements Serializable {     //Serializable 구현
    private static final long serialVersionUID = 1L;

    // filed
    private final UUID id;
    private final Instant createdAt;
    private Instant updatedAt;
    private String username;
    private String email;
    private String password;        // password field 추가

    // 생성자
    public User(String username, String email, String password) {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.updatedAt = createdAt;
        this.username = username;
        this.email = email;
        this.password = password;       // password 할당
    }

    //비즈니스 메서드
    public void update(String username, String email, String password) {
        this.username = username;
        this.email = email;
        if (password != null && !password.isEmpty()) {
            this.password = password;
        }
        this.updatedAt = Instant.now();

    }
}