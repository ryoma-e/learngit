package org.mall.utils;

import org.mall.dto.UserLoginDTO;
import org.mall.dto.UserOnlineDTO;
import org.mall.dto.UserRegisterDTO;
import org.mall.entity.User;

/**
 * DTO与Entity之间相互转换
 */
public class DTOConverters {

   public static UserOnlineDTO convert2UserOnlineDTO(User user) {
      if (null == user) return null;
      return UserOnlineDTO.builder()
              .uid(user.getUid())
              .nickname(user.getNickname())
              .userImageUrl(user.getUserImageUrl())
              .build();
   }

   public static User convert2User(UserLoginDTO userLoginDTO) {
      if (null == userLoginDTO) return null;
      return User.builder()
              .phoneNo(userLoginDTO.getPhoneNo())
              .password(userLoginDTO.getPassword())
              .build();
   }

   public static User convert2User(UserRegisterDTO userRegisterDTO) {
      if (null == userRegisterDTO) return null;
      return User.builder()
              .nickname(userRegisterDTO.getNickname())
              .password(userRegisterDTO.getPassword())
              .phoneNo(userRegisterDTO.getPhoneNo())
              .userName(userRegisterDTO.getUserName())
              .schoolCardID(userRegisterDTO.getSchoolCardID())
              .wechatID(userRegisterDTO.getWechatID())
              .email(userRegisterDTO.getEmail())
              .build();
   }
}
