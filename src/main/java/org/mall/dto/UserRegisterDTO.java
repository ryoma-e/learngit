package org.mall.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 用于接收用户注册信息
 *
 * @author xu wenbo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor // 要有构造函数
public class UserRegisterDTO {
   @NotNull(message = "昵称不能为空")
   private String nickname;

   @NotNull(message = "密码不能为空")
   @Pattern(regexp = "[ \\S]{6,32}", message = "密码为6-32位字符")
   private String password;

   @NotNull(message = "手机号码不能空")
   private String phoneNo;

   @NotNull(message = "姓名不能为空")
   private String userName;

   @NotNull(message = "校园卡号不能为空")
   @Pattern(regexp = "[0-9]{6}", message = "校园卡号要求是6位数字")
   private String schoolCardID;

   @Length(max = 20, message = "微信号为6-20位字符")
   private String wechatID;

   @Email
   private String email;
}
