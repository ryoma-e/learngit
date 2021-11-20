package org.mall.dto;

import org.mall.entity.User;

/**
 * DTO与Entity之间相互转换
 */
public class MyDTOConverters {

   public static UserOnlineDTO convert2UserOnlineDTO(User user) {
      return new UserOnlineDTO(
              user.getUid(),
              user.getNickname(),
              user.getUserImageUrl()
      );
   }

   public static User convert2User(UserLoginDTO userLoginDTO) {
      return User.builder()
              .phoneNo(userLoginDTO.getPhoneNo())
              .password(userLoginDTO.getPassword())
              .build();
   }

   public static User convert2User(UserRegisterDTO userRegisterDTO) {
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
