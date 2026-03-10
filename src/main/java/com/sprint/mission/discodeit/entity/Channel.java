package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.util.UUID;

public class Channel implements Serializable {
    private static final long serialVersionUID = 1L;

    // 필드, 생성자, 메소드 등 정의
    private final UUID id; //고유식별자
    private final long createdAt; // 생성 시각
    private long updatedAt; //마지막 수정 시각
    private String name; // 채널 이름
    private String description; // 채널 설명

    // 생성자: id, createdAt, updatedAt을 생성자 내부에서 초기화, 나머지는 파라미터로 받기
    public Channel(String name, String description) {
        this.id = UUID.randomUUID();
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = createdAt;
        this.name = name;
        this.description = description;
    }

    // 비즈니스 메서드 예시; 정보 업데이트
    public void update(String name, String description) {
        this.name = name;
        this.description = description;
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
    public String getDescription() {

        return description;
    }

    public Channel createChannel(String name) {
        return new Channel(name, "");
    }
}
