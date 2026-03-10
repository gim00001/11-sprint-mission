package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.util.UUID;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    // 필드, 생성자, 메소드 등 정의
    private final UUID id;         // 메시지 고유 ID
    private final long createdAt;  // 생성시각
    private long updatedAt;        // 마지막 수정 시각
    private String content;        // 메시지 내용
    private UUID authorId;           // 작성자 ID
    private UUID channelId;        // 소속 채널 ID

    // 생성자: id, createdAt, updatedAt은 여기서 초기화, 나머지는 파라미터로 받음
    public Message(String content, UUID authorId, UUID channelId) {
        this.id = UUID.randomUUID();
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = createdAt;
        this.content = content;
        this.authorId = authorId;
        this.channelId = channelId;
    }

    // 비즈니스 메소드(메시지 내용 변경)
    public void updateContent(String newContent) {
        this.content = newContent;
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
    public String getContent() {

        return content;
    }
    public UUID getAuthorId() {

        return authorId;
    }
    public UUID getChannelId() {

        return channelId;
    }
}
