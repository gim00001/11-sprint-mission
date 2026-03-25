package com.sprint.mission.discodeit.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {
    private UUID id;
    private String username;
    private String email;
    private String password;
    private byte[] profileImageContent;
    private String profileImageContentType;
}
