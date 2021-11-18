package org.mall.utils;

import lombok.Getter;

// 自定义异常
@Getter
public class APIException extends RuntimeException {
   private int code;
   private String msg;

   public APIException() {
      this(ResultCode.FAILED);
   }

   public APIException(ResultCode faied) {
      this.code = faied.getCode();
      this.msg = faied.getMsg();
   }
}
