package com.sprint.mission.discodit.entity;

import java.util.UUID;

public class Channel {
    // 필드, 생성자, 메소드 등 정의
    private final UUID id; //고유식별자
    private final long createdAt; // 생성 시각
    private long updatedAt; //마지막 수정 시각
    private String name; // 채널 이름
    private String description; //(선택) 채널 설명

    // 생성자: id, createdAt, updatedAt을 생성자 내부에서 초기화, 나머지는 파라미터로 받기
    public Channel(String name, String description) {
        this.id = UUID.randomUUID();
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = createdAt;
        this.name = name;
        this.description = description;
    }
    // Getter 메소드
    public UUID getId() {
        return id;
    }
    public long getCreatedAt() {
        return createdAt;
    }
    public long getUpdatedAt() {
        return createdAt;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }

    // update 메서드 예시; 채널 이름 또는 설명 변경
    public void update(String name, String description) {
        this.name = name;
        this.description = description;
        this.updatedAt = System.currentTimeMillis();
    }

    public Channel createChannel(String 자유채널) {
        return null;
    }
}
