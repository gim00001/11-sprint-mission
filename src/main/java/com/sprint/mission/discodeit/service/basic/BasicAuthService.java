package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.AuthLoginRequestDto;
import com.sprint.mission.discodeit.dto.UserResponseDto;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicAuthService implements AuthService {
    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;

    @Override
    public UserResponseDto login(AuthLoginRequestDto dto) {
        User user = userRepository.findByUsername(dto.getUsername())
                .filter(u -> u.getPassword().equals(dto.getPassword()))
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 로그인 정보입니다."));

        // UserStatus 추출예시 (findAllByUserId가 List 반환인 경우)
        UserStatus userStatus = userStatusRepository.findAllByUserId(user.getId()).stream()
                .findFirst()
                .orElse(null);
        return toUserResponseDto(user, userStatus);
    }

    private UserResponseDto toUserResponseDto(User user, UserStatus userStatus) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setOnline(userStatus != null && userStatus.isOnline());
        return dto;
    }
}
