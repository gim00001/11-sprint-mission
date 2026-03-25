package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.UserStatusCreateRequestDto;
import com.sprint.mission.discodeit.dto.UserStatusResponseDto;
import com.sprint.mission.discodeit.dto.UserStatusUpdateByUserIdRequestDto;
import com.sprint.mission.discodeit.dto.UserStatusUpdateRequestDto;

import java.util.List;
import java.util.UUID;

public interface UserStatusService {
    UserStatusResponseDto create(UserStatusCreateRequestDto dto);

    UserStatusResponseDto findById(UUID id);

    List<UserStatusResponseDto> findAll();

    UserStatusResponseDto update(UserStatusUpdateRequestDto dto);

    UserStatusResponseDto updateByUserId(UserStatusUpdateByUserIdRequestDto dto);

    void delete(UUID id);
}
