package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.*;

public class BasicMessageService implements MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;

    public BasicMessageService(MessageRepository messageRepository, UserRepository userRepository, ChannelRepository channelRepository) {

        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.channelRepository = channelRepository;
    }

    @Override
    public Message create(String content, UUID channelId, UUID authorId) {
        // 메시지 유효성 검사
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("메시지 내용을 입력하세요.");
        }
        // 연관 도메인 검증 추가
        if (userRepository.findById(authorId).isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
        }
        if (channelRepository.findById(channelId).isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 채널입니다.");
        }
        Message message = new Message(content, authorId, channelId);
        return messageRepository.save(message);
    }

    @Override
    public Optional<Message> findById(UUID id) {
        return messageRepository.findById(id);
    }

    @Override
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public void delete(UUID id) {
        messageRepository.delete(id);
    }

    @Override
    public List<Message> findByAuthorId(UUID authorId) {
        return messageRepository.findByAuthorId(authorId);
    }

    @Override
    public List<Message> findByChannelId(UUID channelId) {
        return messageRepository.findByChannelId(channelId);

    }
}
