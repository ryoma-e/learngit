package org.mall.utils;

import lombok.Data;
import lombok.ToString;

/**
 * 自定义统一响应体, 用于统一包装返回的json对象
 *
 * VO (View Object): 显示层对象, 通常是Web向模板渲染引擎层传输的对象
 *
 * @param <T> 响应数据data的类型
 *
 * @author xu wenbo
 */
@Data
public class ResultVO<T> {
   /**
    * 响应码, 指示响应处理结果(成功, 参数错误, 无权限等等)
    * @see ResultCode
    */
   private int code;
   /**
    * 响应信息, 结合响应码说明响应情况
    * @see ResultCode
    */
   private String msg;

   /**
    * 响应内容数据(即请求的数据内容, 如果响应状态为错误状态, 则该部分内容为null)
    */
   private T data;

   /**
    * 默认响应状态: 成功
    */
   public ResultVO(T data) {
      this(ResultCode.SUCCESS, data);
   }

   public ResultVO(ResultCode resultCode, T data) {
      this.code = resultCode.getCode();
      this.msg = resultCode.getMsg();
      this.data = data;
   }
}
