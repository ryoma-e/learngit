package org.mall.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException; // 注意包名
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * 全局异常处理
 * TODO: 还有其他异常要处理(参见博客)
 *
 * 注解 @RestControllerAdvice 可以在一个类中处理多个Controller类在异常，这样所有控制器的异常可以在一个地方进行处理
 * 还有一个类似的 @ControllerAdvice 注解; 可以参考 @Controller 与 @RestController 来理解
 *
 * @author xu wenbo
 */
@RestControllerAdvice({"org.mall.controller"})
@Slf4j   // 日志相关
public class GlobalExceptionHandler {
   /**
    * 自定义异常 APIException
    * @see APIException
    *
    * @return 经过统一返回接口ResultVO包装的错误信息(JSON, 给客户端, 用JavaScript接收和处理)
    * @see ResultVO
    */
   @ExceptionHandler(APIException.class)     // 指定要处理的异常类(当然也可以采用默认映射)
   @ResponseStatus(HttpStatus.BAD_REQUEST)   // controller层如果不能处理相应的请求(路径未定义等), 将返回 BAD_REQUEST
   public ResultVO<Object> APIExceptionHandler(APIException e) {
      log.error(e.toString());
      return new ResultVO<>(ResultCode.FAILED, e.getMsg()); // 包装错误信息并返回给客户端
   }

   /**
    * Controller 层参数校验失败的统一处理, 就不用每个函数都添加判断逻辑了
    * @return ResultVO包装的错误信息
    */
   @ExceptionHandler(BindException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   public ResultVO<Object> BindExceptionHandler(BindException e) {
      log.error("参数校验失败");
      List<String> list = new ArrayList<>();
      if (!e.hasErrors()){
         for(ObjectError error:e.getBindingResult().getAllErrors()){
            list.add(error.getDefaultMessage());
         }
      }
      // 然后提取错误提示信息进行返回
      return new ResultVO<>(ResultCode.VALIDATE_FAILED, list);
   }

   /**
    * TODO: 还不知道是处理哪种异常的
    * @return ResultVO包装的错误信息
    */
   @ExceptionHandler(MethodArgumentNotValidException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   public ResultVO<Object> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
      log.error("*** MethodArgumentNotValidException ***");
      List<String> list = new ArrayList<>();
      if (!e.getBindingResult().getAllErrors().isEmpty()){
         for(ObjectError error:e.getBindingResult().getAllErrors()){
            list.add(error.getDefaultMessage());
         }
      }
      // 然后提取错误提示信息进行返回
      return new ResultVO<>(ResultCode.VALIDATE_FAILED, list);
   }
}
