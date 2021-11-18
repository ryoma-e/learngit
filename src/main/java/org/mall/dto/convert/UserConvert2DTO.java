package org.mall.dto.convert;

import org.mall.dto.UserOnlineDTO;
import org.mall.entity.User;

public class UserConvert2DTO {
   public static UserOnlineDTO convert2UserOnlineDTO(User user) {
      return new UserOnlineDTO(
              user.getUid(),
              user.getNickname(),
              user.getUserImageUrl()
      );
   }
}
