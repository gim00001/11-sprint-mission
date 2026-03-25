package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.BinaryContentCreateRequestDto;
import com.sprint.mission.discodeit.dto.BinaryContentResponseDto;

import java.util.List;
import java.util.UUID;

public interface BinaryContentService {
    BinaryContentResponseDto create(BinaryContentCreateRequestDto dto);

    BinaryContentResponseDto findById(UUID id);

    List<BinaryContentResponseDto> findAllByIdIn(List<UUID> ids);

    void delete(UUID id);
}
