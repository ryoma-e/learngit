package org.mall.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class AdminLoginDTO {
   @NotNull(message = "手机号码不能为空")
   private String phoneNo;

   @NotNull(message = "密码不能为空")
   @Pattern(regexp = "[ \\S]{6,32}", message = "密码为6-32位字符")
   private String password;
}
