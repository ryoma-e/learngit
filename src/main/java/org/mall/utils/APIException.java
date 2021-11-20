package org.mall.utils;

import lombok.Getter;

/**
 * 自定义异常(运行时异常), 方便中断业务处理并返回错误信息
 *
 * 异常抛出后要进行捕获并处理
 * (使用统一返回接口ResultVO包装后返回给客户端)
 * @see GlobalExceptionHandler
 * @see ResultVO
 *
 * @author Xu Wenbo
 */
@Getter
public class APIException extends RuntimeException {
   private final int code;
   private final String msg;

   /**
    * 默认使用``FAILED 接口参数错误``的描述
    * @see ResultCode
    */
   public APIException() {
      this(ResultCode.FAILED);
   }

   public APIException(ResultCode failed) {
      this.code = failed.getCode();
      this.msg = failed.getMsg();
   }
}
