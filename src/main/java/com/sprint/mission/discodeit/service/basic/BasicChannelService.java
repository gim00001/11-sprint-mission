package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.request.PrivateChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelUpdateRequest;
import com.sprint.mission.discodeit.dto.response.BinaryContentDto;
import com.sprint.mission.discodeit.dto.response.ChannelDto;
import com.sprint.mission.discodeit.dto.response.UserDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Channel.ChannelType;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasicChannelService implements ChannelService {

  private final ChannelRepository channelRepository;
  private final ReadStatusRepository readStatusRepository;
  private final MessageRepository messageRepository;
  private final UserRepository userRepository;
  private final UserStatusRepository userStatusRepository;

  @Override
  @Transactional
  public ChannelDto createPublic(PublicChannelCreateRequest request) {
    Channel channel = new Channel(ChannelType.PUBLIC, request.name(), request.description());
    channelRepository.save(channel);
    return toDto(channel);
  }

  @Override
  @Transactional
  public ChannelDto createPrivate(PrivateChannelCreateRequest request) {
    Channel channel = new Channel(ChannelType.PRIVATE, null, null);
    channelRepository.save(channel);

    for (UUID userId : request.participantIds()) {
      User user = userRepository.findById(userId).orElse(null);
      if (user == null) {
        throw new RuntimeException("User not found: " + userId);
      }
      ReadStatus readStatus = new ReadStatus(user, channel, Instant.now());
      readStatusRepository.save(readStatus);
    }
    return toDto(channel);
  }

  @Override
  public ChannelDto findById(UUID id) {
    Channel channel = channelRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Channel with id " + id + " not found"));
    return toDto(channel);
  }

  @Override
  public List<ChannelDto> findAllByUserId(UUID userId) {
    List<Channel> publicChannels = channelRepository.findAllByType(ChannelType.PUBLIC);
    List<Channel> privateChannels = readStatusRepository.findAllByUserId(userId).stream()
        .map(ReadStatus::getChannel)
        .filter(ch -> ch.getType() == ChannelType.PRIVATE)
        .toList();

    return java.util.stream.Stream.concat(publicChannels.stream(), privateChannels.stream())
        .map(this::toDto)
        .toList();
  }

  @Override
  @Transactional
  public ChannelDto update(UUID channelId, PublicChannelUpdateRequest request) {
    Channel channel = channelRepository.findById(channelId)
        .orElseThrow(
            () -> new IllegalArgumentException("Channel with id " + channelId + " not found"));
    if (channel.getType() == ChannelType.PRIVATE) {
      throw new IllegalArgumentException("Private channel cannot be updated");
    }
    channel.update(request.newName(), request.newDescription());
    return toDto(channel);
  }

  @Override
  @Transactional
  public void delete(UUID id) {
    Channel channel = channelRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Channel with id " + id + " not found"));
    channelRepository.delete(channel);
  }

  private ChannelDto toDto(Channel channel) {
    Instant lastMessageAt = messageRepository.findAllByChannelId(channel.getId()).stream()
        .map(Message::getCreatedAt)
        .max(Instant::compareTo)
        .orElse(null);

    List<UserDto> participants = new ArrayList<>();
    if (channel.getType() == ChannelType.PRIVATE) {
      participants = readStatusRepository.findAllByChannelId(channel.getId()).stream()
          .map(rs -> {
            User user = rs.getUser();
            UserStatus status = userStatusRepository.findByUserId(user.getId()).orElse(null);

            BinaryContentDto profileDto = null;
            if (user.getProfile() != null) {
              BinaryContent profile = user.getProfile();
              profileDto = new BinaryContentDto(
                  profile.getId(),
                  profile.getFileName(),
                  profile.getSize(),
                  profile.getContentType()
              );
            }

            return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                profileDto,           // ← user.getProfile().getId() → profileDto로 변경
                status != null && status.isOnline()
            );
          })
          .toList();
    }
    return new ChannelDto(      // ← 이게 빠졌어요!
        channel.getId(),
        channel.getType().name(),
        channel.getName(),
        channel.getDescription(),
        participants,
        lastMessageAt
    );
  }
}
