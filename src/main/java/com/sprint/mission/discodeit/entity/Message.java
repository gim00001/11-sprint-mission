package com.sprint.mission.discodit.entity;

import java.util.UUID;

public class Message {
    // 필드, 생성자, 메소드 등 정의
    private final UUID id;         // 메시지 고유 ID
    private final long createdAt;  // 생성시각
    private long updatedAt;        // 마지막 수정 시각
    private String content;        // 메시지 내용
    private UUID userId;           // 작성자 ID
    private UUID channelId;        // 소속 채널 ID

    // 생성자: id, createdAt, updatedAt은 여기서 초기화, 나머지는 파라미터로 받음
    public Message(String content, UUID userId, UUID channelId) {
        this.id = UUID.randomUUID();
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = createdAt;
        this.content = content;
        this.userId = userId;
        this.channelId = channelId;
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
    public String getContent() {
        return content;
    }
    public UUID getUserId() {
        return userId;
    }
    public UUID getChannelId() {
        return channelId;
    }

    // 업데이트 메소드(메시지 내용 변경)
    public void update(String newContent) {
        this.content = newContent;
        this.updatedAt = System.currentTimeMillis();
    }
}
