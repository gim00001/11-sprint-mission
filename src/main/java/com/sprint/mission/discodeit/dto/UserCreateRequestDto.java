package com.sprint.mission.discodeit.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserCreateRequestDto {
    private String username;
    private String email;
    private String password;
    private byte[] profileImageContent; // 프로필 이미지
    private String profileImageContentType; // 프로필 이미지
}
