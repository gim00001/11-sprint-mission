package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.UserStatusCreateRequestDto;
import com.sprint.mission.discodeit.dto.UserStatusResponseDto;
import com.sprint.mission.discodeit.dto.UserStatusUpdateByUserIdRequestDto;
import com.sprint.mission.discodeit.dto.UserStatusUpdateRequestDto;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicUserStatusService implements UserStatusService {
    private final UserStatusRepository userStatusRepository;
    private final UserRepository userRepository;

    @Override
    public UserStatusResponseDto create(UserStatusCreateRequestDto dto) {
        // 유저 존재 확인
        userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        // 중복체크
        boolean exists = userStatusRepository.findAll().stream()
                .anyMatch(status -> status.getUserId().equals(dto.getUserId()));
        if (exists) {
            throw new IllegalArgumentException("이미 해당 User의 상태가 존재합니다.");
        }
        UserStatus entity = new UserStatus(dto.getUserId(), dto.getLastAccessAt());
        userStatusRepository.save(entity);
        return toResponseDto(entity);
    }

    @Override
    public UserStatusResponseDto findById(UUID id) {
        UserStatus entity = userStatusRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 UserStatus 없음"));
        return toResponseDto(entity);
    }

    @Override
    public List<UserStatusResponseDto> findAll() {
        return userStatusRepository.findAll().stream()
                .map(this::toResponseDto).toList();
    }

    @Override
    public UserStatusResponseDto update(UserStatusUpdateRequestDto dto) {
        UserStatus entity = userStatusRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 UserStatus 없음"));
        entity.setLastAccessAt(dto.getLastAccessAt());
        userStatusRepository.save(entity);
        return toResponseDto(entity);
    }

    @Override
    public UserStatusResponseDto updateByUserId(UserStatusUpdateByUserIdRequestDto dto) {
        UserStatus entity = userStatusRepository.findAll().stream()
                .filter(status -> status.getUserId().equals(dto.getUserId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 UserId의 UserStatus 없음"));
        entity.setLastAccessAt(dto.getLastAccessAt());
        userStatusRepository.save(entity);
        return toResponseDto(entity);
    }

    @Override
    public void delete(UUID id) {
        userStatusRepository.deleteById(id);
    }

    private UserStatusResponseDto toResponseDto(UserStatus entity) {
        UserStatusResponseDto dto = new UserStatusResponseDto();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setLastAccessAt(entity.getLastAccessAt());
        dto.setOnline(entity.isOnline());
        return dto;
    }

}
