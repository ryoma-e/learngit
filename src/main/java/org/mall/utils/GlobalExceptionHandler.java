package org.mall.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException; // 注意包名
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice({"org.mall.controller"})
@Slf4j  // 与 log 对象有关
public class GlobalExceptionHandler {
   @ExceptionHandler(APIException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   public ResultVO<Object> APIExceptionHandler(APIException e) {
      log.error("api异常");
      return new ResultVO<>(ResultCode.FAILED, e.getMsg());
   }

   // 与 @Valid 有关??
   @ExceptionHandler(MethodArgumentNotValidException.class)
   public ResultVO<Object> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
      log.error("方法参数错误异常");
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
