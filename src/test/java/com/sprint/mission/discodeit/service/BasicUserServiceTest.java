package com.sprint.mission.discodeit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserUpdateRequest;
import com.sprint.mission.discodeit.dto.response.UserDto;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.exception.UserAlreadyExistsException;
import com.sprint.mission.discodeit.exception.UserNotFoundException;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.basic.BasicUserService;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BasicUserServiceTest {

  @Mock
  private UserRepository userRepository;
  @Mock
  private UserStatusRepository userStatusRepository;
  @Mock
  private BinaryContentRepository binaryContentRepository;
  @Mock
  private BinaryContentStorage binaryContentStorage;
  @Mock
  private UserMapper userMapper;

  @InjectMocks
  private BasicUserService userService;

  // ───── create ─────

  @Test
  void create_성공() {
    // given
    UserCreateRequest request = new UserCreateRequest("jihye", "jihye@test.com", "password123");
    User user = new User("jihye", "jihye@test.com", "password123", null);
    UserStatus status = new UserStatus(user, java.time.Instant.now());
    UserDto dto = new UserDto(user.getId(), "jihye", "jihye@test.com", null, false);

    given(userRepository.existsByUsername("jihye")).willReturn(false);
    given(userRepository.existsByEmail("jihye@test.com")).willReturn(false);
    given(userRepository.save(any(User.class))).willReturn(user);
    given(userStatusRepository.save(any(UserStatus.class))).willReturn(status);
    given(userStatusRepository.findByUserId(any(UUID.class))).willReturn(Optional.of(status));
    given(userMapper.toDto(any(User.class), any(UserStatus.class))).willReturn(dto);

    // when
    UserDto result = userService.create(request, null);

    // then
    assertThat(result).isNotNull();
    assertThat(result.username()).isEqualTo("jihye");
  }

  @Test
  void create_실패_중복username() {
    // given
    UserCreateRequest request = new UserCreateRequest("jihye", "jihye@test.com", "password123");
    given(userRepository.existsByUsername("jihye")).willReturn(true);

    // when & then
    assertThatThrownBy(() -> userService.create(request, null))
        .isInstanceOf(UserAlreadyExistsException.class);
  }

  @Test
  void create_실패_중복email() {
    // given
    UserCreateRequest request = new UserCreateRequest("jihye", "jihye@test.com", "password123");
    given(userRepository.existsByUsername("jihye")).willReturn(false);
    given(userRepository.existsByEmail("jihye@test.com")).willReturn(true);

    // when & then
    assertThatThrownBy(() -> userService.create(request, null))
        .isInstanceOf(UserAlreadyExistsException.class);
  }

  // ───── update ─────

  @Test
  void update_성공() {
    // given
    UUID userId = UUID.randomUUID();
    UserUpdateRequest request = new UserUpdateRequest("newName", "new@test.com", "newPassword123");
    User user = new User("jihye", "jihye@test.com", "password123", null);
    UserStatus status = new UserStatus(user, java.time.Instant.now());
    UserDto dto = new UserDto(userId, "newName", "new@test.com", null, false);

    given(userRepository.findById(userId)).willReturn(Optional.of(user));
    given(userRepository.existsByUsername("newName")).willReturn(false);
    given(userRepository.existsByEmail("new@test.com")).willReturn(false);
    given(userStatusRepository.findByUserId(any(UUID.class))).willReturn(Optional.of(status));
    given(userMapper.toDto(any(User.class), any(UserStatus.class))).willReturn(dto);

    // when
    UserDto result = userService.update(userId, request, null);

    // then
    assertThat(result).isNotNull();
    assertThat(result.username()).isEqualTo("newName");
  }

  @Test
  void update_실패_존재하지않는유저() {
    // given
    UUID userId = UUID.randomUUID();
    UserUpdateRequest request = new UserUpdateRequest("newName", "new@test.com", "newPassword123");
    given(userRepository.findById(userId)).willReturn(Optional.empty());

    // when & then
    assertThatThrownBy(() -> userService.update(userId, request, null))
        .isInstanceOf(UserNotFoundException.class);
  }

  // ───── delete ─────

  @Test
  void delete_성공() {
    // given
    UUID userId = UUID.randomUUID();
    User user = new User("jihye", "jihye@test.com", "password123", null);
    given(userRepository.findById(userId)).willReturn(Optional.of(user));

    // when
    userService.delete(userId);

    // then
    then(userRepository).should().delete(user);
  }

  @Test
  void delete_실패_존재하지않는유저() {
    // given
    UUID userId = UUID.randomUUID();
    given(userRepository.findById(userId)).willReturn(Optional.empty());

    // when & then
    assertThatThrownBy(() -> userService.delete(userId))
        .isInstanceOf(UserNotFoundException.class);
  }
}