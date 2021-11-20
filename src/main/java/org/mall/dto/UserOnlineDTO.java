package org.mall.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用于传输用户的可展示信息给客户端
 * 同时, 用户成功登录后将作为 user_online 属性保存在 session 中,
 * 用于后续校验用户是否已登录
 *
 * @author xu wenbo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserOnlineDTO {
   private String uid;
   private String nickname;
   private String userImageUrl;
}
