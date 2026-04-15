package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.request.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageUpdateRequest;
import com.sprint.mission.discodeit.dto.response.BinaryContentDto;
import com.sprint.mission.discodeit.dto.response.MessageDto;
import com.sprint.mission.discodeit.dto.response.UserDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasicMessageService implements MessageService {

  private final MessageRepository messageRepository;
  private final ChannelRepository channelRepository;
  private final UserRepository userRepository;
  private final BinaryContentRepository binaryContentRepository;
  private final BinaryContentStorage binaryContentStorage;
  private final UserStatusRepository userStatusRepository;

  @Override
  @Transactional
  public MessageDto create(MessageCreateRequest request, List<MultipartFile> attachments) {
    Channel channel = channelRepository.findById(request.channelId())
        .orElseThrow(() -> new IllegalArgumentException(
            "Channel with id " + request.channelId() + " not found"));
    User author = userRepository.findById(request.authorId())
        .orElseThrow(() -> new IllegalArgumentException(
            "Author with id " + request.authorId() + " not found"));

    Message message = new Message(request.content(), channel, author);

    if (attachments != null) {
      attachments.forEach(file -> {
        try {
          BinaryContent bc = new BinaryContent(
              file.getOriginalFilename(),
              file.getSize(),
              file.getContentType()
          );
          binaryContentRepository.save(bc);
          binaryContentStorage.put(bc.getId(), file.getBytes());
          message.addAttachment(bc);
        } catch (Exception e) {
          throw new RuntimeException("파일 저장 실패", e);
        }
      });
    }

    messageRepository.saveAndFlush(message);
    return toDto(message);
  }

  @Override
  public List<MessageDto> findAllByChannelId(UUID channelId) {
    return messageRepository.findAllByChannelId(channelId).stream()
        .map(this::toDto)
        .toList();
  }

  @Override
  @Transactional
  public MessageDto update(UUID messageId, MessageUpdateRequest request) {
    Message message = messageRepository.findById(messageId)
        .orElseThrow(
            () -> new IllegalArgumentException("Message with id " + messageId + " not found"));
    message.updateContent(request.newContent());
    return toDto(message);
  }

  @Override
  @Transactional
  public void delete(UUID messageId) {
    Message message = messageRepository.findById(messageId)
        .orElseThrow(
            () -> new IllegalArgumentException("Message with id " + messageId + " not found"));
    messageRepository.delete(message);
  }

  private MessageDto toDto(Message message) {
    UserDto authorDto = null;
    if (message.getAuthor() != null) {
      User author = message.getAuthor();
      UserStatus status = userStatusRepository.findByUserId(author.getId()).orElse(null);

      BinaryContentDto profileDto = null;
      if (author.getProfile() != null) {
        BinaryContent profile = author.getProfile();
        profileDto = new BinaryContentDto(
            profile.getId(),
            profile.getFileName(),
            profile.getSize(),
            profile.getContentType()
        );
      }

      authorDto = new UserDto(
          author.getId(),
          author.getUsername(),
          author.getEmail(),
          profileDto,  // ← author.getProfile().getId() → profileDto로 변경
          status != null && status.isOnline()
      );
    }

    List<BinaryContentDto> attachmentDtos = message.getAttachments().stream()
        .map(bc -> new BinaryContentDto(
            bc.getId(),
            bc.getFileName(),
            bc.getSize(),
            bc.getContentType()
        ))
        .toList();

    return new MessageDto(
        message.getId(),
        message.getCreatedAt(),
        message.getUpdatedAt(),
        message.getContent(),
        message.getChannel().getId(),
        authorDto,
        attachmentDtos
    );
  }
}