package com.sprint.mission.discodeit.dto.response;

import java.util.UUID;

public record BinaryContentDto(UUID id, String filename, Long size, String contentType) {

}
