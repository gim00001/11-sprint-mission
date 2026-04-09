package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class User extends BaseUpdatableEntity {     //Serializable 구현

  private static final long serialVersionUID = 1L;

  private String username;
  private String email;
  private String password;
  private BinaryContent profile;

  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }

  public User(String username, String email, String password, BinaryContent profile) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.profile = profile;
  }

  public void update(String newUsername, String newEmail, String newPassword) {
    if (newUsername != null && !newUsername.isEmpty()) {
      this.username = newUsername;
    }
    if (newEmail != null && !newEmail.isEmpty()) {
      this.email = newEmail;
    }
    if (newPassword != null && !newPassword.isEmpty()) {
      this.password = newPassword;
    }
  }

  public void updateProfile(BinaryContent profile) {
    this.profile = profile;
  }
}