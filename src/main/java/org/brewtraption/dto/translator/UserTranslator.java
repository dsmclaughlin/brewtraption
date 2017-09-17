/**
 * Copyright (c) 2016 Owain Lewis
 */
package org.brewtraption.dto.translator;

import org.brewtraption.dto.UserDTO;
import org.brewtraption.entity.UserEntity;

public class UserTranslator {

  public static UserDTO toDTO(final UserEntity userEntity) {
    return new UserDTO.Builder().userName(userEntity.getUsername())
      .firstName(userEntity.getFirstName()).lastName(userEntity.getLastName()).build();
  }

  public static UserEntity toEntity(final UserDTO user) {
    UserEntity userEntity = new UserEntity();
    userEntity.setFirstName(user.getFirstName());
    userEntity.setLastName(user.getLastName());
    userEntity.setUsername(user.getUserName());
    return userEntity;
  }

}
