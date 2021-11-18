package org.mall.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
   private String uid;
   private String nickname;
   private String password;
   private int accountStatus;
   private String userImageUrl;
   private String phoneNo;
   private String userName;
   private String schoolCardID;
   private int gender;
   private String wechatID;
   private String email;
   private String registerTime;
}
