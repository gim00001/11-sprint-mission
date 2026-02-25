package com.sprint.mission.discodit.entity;
import java.util.UUID;

public class User {
        // 필드, 생성자, 메소드 등 정의
        //필드
        private final UUID id;
        private final long createdAt;
        private long updatedAt;
        private String name;
        private String email;

        //생성자
        public User(String name, String email) {
            this.id = UUID.randomUUID();
            this.createdAt = System.currentTimeMillis();
            this.updatedAt = createdAt;
            this.name = name;
            this.email = email;
        }
     // 비지니스 메서드
        public void update(String name, String email) {
            this.name = name;
            this.email = email;
            this.updatedAt = System.currentTimeMillis();
        }


        // Getter 메소드
    public UUID getId() {
           return id;
        }
    public long getCreatedAt() {
        return createdAt;
    }
    public long getUpdatedAt() {
        return updatedAt;
    }
    public String getName() {
            return name;
        }
    public String getEmail() {
            return email;
        }

}
