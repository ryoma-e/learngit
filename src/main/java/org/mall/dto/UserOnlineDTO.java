package org.mall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOnlineDTO {
   private String uid;
   private String nickname;
   private String userImageUrl;
}
