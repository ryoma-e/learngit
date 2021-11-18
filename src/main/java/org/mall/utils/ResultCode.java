package org.mall.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {

   SUCCESS(1000, "操作成功"),
   FAILED(1001, "接口错误"),
   VALIDATE_FAILED(1002, "参数校验失败"),
   ERROR(1003, "未知错误"),

   //2000系列用户错误
   USER_NOT_EXIST(2000,"用户不存在"),
   USER_LOGIN_FAILED(2001,"手机号或密码错误"),
   USER_NOT_LOGIN(2002,"用户还未登录,请先登录"),
   NO_PERMISSION(2003,"权限不足,请联系管理员"),
   USER_ALREADY_LOGIN(2004, "请勿重复登录");

   private final int code;
   private final String msg;
}
