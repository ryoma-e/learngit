package org.mall.utils;

import lombok.Data;

@Data
public class ResultVO<T> {
   // 状态码
   private int code;
   private String msg;
   private T data;

   public ResultVO(T data) {
      this(ResultCode.SUCCESS, data);
   }

   public ResultVO(ResultCode resultCode, T data) {
      this.code = resultCode.getCode();
      this.msg = resultCode.getMsg();
      this.data = data;
   }
}
