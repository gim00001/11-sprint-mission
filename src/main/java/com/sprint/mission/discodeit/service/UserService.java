package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.UserCreateRequestDto;
import com.sprint.mission.discodeit.dto.UserResponseDto;
import com.sprint.mission.discodeit.dto.UserUpdateRequestDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponseDto create(UserCreateRequestDto dto);

    UserResponseDto findById(UUID id);

    List<UserResponseDto> findAll();

    UserResponseDto update(UserUpdateRequestDto dto);

    void delete(UUID id);
}
