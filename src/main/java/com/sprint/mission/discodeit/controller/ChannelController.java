package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.ChannelCreateRequestDto;
import com.sprint.mission.discodeit.dto.ChannelResponseDto;
import com.sprint.mission.discodeit.dto.ChannelUpdateRequestDto;
import com.sprint.mission.discodeit.service.ChannelService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/channels")
public class ChannelController {

  private final ChannelService channelService;

  // 1. 공개 채널 상성 (공개/비공개 통합)
  @PostMapping
  public ChannelResponseDto createPublicChannel(@RequestBody ChannelCreateRequestDto dto) {
    return channelService.createPublic(dto);
  }

  // 3. 채널 정보 수정(PUT 또는 PATCH 중 REST 기준에 맞게 선택, 부분변경이면 PATCH가 더 보편적이다.)
  @PatchMapping("/{channelId}")
  public ChannelResponseDto updateChannel(
      @PathVariable UUID channelId,
      @RequestBody ChannelUpdateRequestDto dto) {
    return channelService.update(channelId, dto);
  }

  // 4. 채널 삭제
  @DeleteMapping("/{channelId}")
  public void deleteChannel(@PathVariable UUID channelId) {
    channelService.delete(channelId);
  }

  // 5. 특정 사용자가 볼 수 있는 채널 목록(공개+참여 비공개)
  @GetMapping
  public List<ChannelResponseDto> findAllChannels(@RequestParam UUID userId) {
    return channelService.findAllByUserId(userId);
  }
}
