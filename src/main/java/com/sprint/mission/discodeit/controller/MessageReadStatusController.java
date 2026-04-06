package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.ReadStatusCreateRequestDto;
import com.sprint.mission.discodeit.dto.ReadStatusResponseDto;
import com.sprint.mission.discodeit.dto.ReadStatusUpdateRequestDto;
import com.sprint.mission.discodeit.service.ReadStatusService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/channels/{channelId}/messages/{messageId}/read-status")
@RequiredArgsConstructor
public class MessageReadStatusController {

  private final ReadStatusService readStatusService;

  // 메시지 수신정보(읽음/미읽음) 등록
  @PostMapping
  public ReadStatusResponseDto createReadStatus(
      @PathVariable UUID channelId,
      @PathVariable UUID messageId,
      @RequestBody ReadStatusCreateRequestDto dto) {
    return readStatusService.createReadStatus(channelId, messageId, dto);
  }

  // 수신정보 수정
  @PatchMapping("/{readStatusId}")
  public ReadStatusResponseDto updateReadStatus(
      @PathVariable UUID channelId,
      @PathVariable UUID messageId,
      @PathVariable UUID readStatusId,
      @RequestBody ReadStatusUpdateRequestDto dto) {
    return readStatusService.updateReadStatus(channelId, messageId, readStatusId, dto);
  }

  // 특정 사용자의 수신정보 목록 조회(엔드포인트 자유)
  @GetMapping("/user")
  public List<ReadStatusResponseDto> findReadStatusByUserId(
      @RequestParam UUID userId) {
    return readStatusService.findReadStatusByUserId(userId);
  }
}
