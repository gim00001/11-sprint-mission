package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    // 필드, 생성자, 메소드 등 정의
    private final UUID id;         // 메시지 고유 ID
    private final Instant createdAt;  // 생성시각
    private Instant updatedAt;        // 마지막 수정 시각
    private String content;        // 메시지 내용
    private UUID authorId;           // 작성자 ID
    private UUID channelId;        // 소속 채널 ID

    // 생성자: id, createdAt, updatedAt은 여기서 초기화, 나머지는 파라미터로 받음
    public Message(String content, UUID authorId, UUID channelId) {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.updatedAt = createdAt;
        this.content = content;
        this.authorId = authorId;
        this.channelId = channelId;
    }

    // 기존 데이터 (long -> Instant 변환)용 생성자
    public Message(UUID id, long createdAt, long updatedAt, String content, UUID authorId, UUID channelId) {
        this.id = id;
        this.createdAt = Instant.ofEpochMilli(createdAt);
        this.updatedAt = Instant.ofEpochMilli(updatedAt);
        this.content = content;
        this.authorId = authorId;
        this.channelId = channelId;
    }

    // 비즈니스 메소드(메시지 내용 변경)
    public void updateContent(String newContent) {
        this.content = newContent;
        this.updatedAt = Instant.now();
    }
}
