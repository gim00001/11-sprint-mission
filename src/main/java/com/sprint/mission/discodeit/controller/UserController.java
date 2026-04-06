package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.LoginRequestDto;
import com.sprint.mission.discodeit.dto.LoginResponseDto;
import com.sprint.mission.discodeit.dto.UserCreateRequestDto;
import com.sprint.mission.discodeit.dto.UserDto;
import com.sprint.mission.discodeit.dto.UserResponseDto;
import com.sprint.mission.discodeit.dto.UserStatusResponseDto;
import com.sprint.mission.discodeit.dto.UserUpdateRequestDto;
import com.sprint.mission.discodeit.service.UserService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping
  public ResponseEntity<List<UserDto>> findAllUsers() {
    List<UserDto> users = userService.getAllUsers();
    return ResponseEntity.ok(users);
  }

  // 1. 사용자 등록
  @PostMapping
  public UserResponseDto createUser(@RequestBody UserCreateRequestDto requestDto) {
    return userService.create(requestDto);
  }

  // 2. 사용자 정보 수정
  @PutMapping("/{id}")
  public UserResponseDto updateUser(
      @PathVariable UUID id,
      @RequestBody UserUpdateRequestDto updateRequestDto) {
    return userService.update(id, updateRequestDto);
  }

  // 3. 사용자 삭제
  @DeleteMapping("/{id}")
  public void deleteUser(@PathVariable UUID id) {
    userService.delete(id);
  }


  // 5. 사용자의 온라인 상태 업데이트
  @PatchMapping("/{id}/online")
  public UserStatusResponseDto updateOnlineStatus(
      @PathVariable UUID id,
      @RequestBody UserStatusResponseDto statusUpdateRequestDto) {
    return userService.updateOnlineStatus(id, statusUpdateRequestDto);
  }

  // 6. 사용자 로그인 검증
  @PostMapping("/login")
  public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
    // 실제로는 userService.login(loginRequestDto) 등으로 검증!
    boolean isSuccess = userService.login(loginRequestDto);

    if (isSuccess) {
      return new LoginResponseDto("로그인 성공!");
    } else {
      return new LoginResponseDto("이메일 또는 비밀번호가 올바르지 않습니다.");
    }
  }

}
