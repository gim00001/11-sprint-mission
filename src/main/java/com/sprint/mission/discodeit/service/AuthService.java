package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.AuthLoginRequestDto;
import com.sprint.mission.discodeit.dto.UserResponseDto;

public interface AuthService {
    UserResponseDto login(AuthLoginRequestDto dto);
}
