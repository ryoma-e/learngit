package org.mall.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 用于接收用户登录信息
 *
 * @author xuwenbo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {
   @NotNull(message = "手机号码不能为空")
   private String phoneNo;

   @NotNull(message = "密码不能为空")
   @Pattern(regexp = "[ \\S]{6,32}", message = "密码为6-32位字符")
   private String password;
}
