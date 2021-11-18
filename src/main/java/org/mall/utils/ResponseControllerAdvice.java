package org.mall.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

// https://cloud.tencent.com/developer/article/1699840

// 全局处理增强版Controller，避免Controller里返回数据每次都要用响应体来包装
@RestControllerAdvice(basePackages = {"org.mall.controller"})
@Slf4j
public class ResponseControllerAdvice implements ResponseBodyAdvice<Object> {
   @Override
   public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> aClass) {
      // 如果接口返回的类型本身就是ResultVO那就没有必要进行额外的操作，返回false
//      log.info(returnType.getGenericParameterType().toString());
//      log.info(ResultVO.class.toString());
//      log.info(ResultVO.class.toGenericString());
//      log.info(returnType.getParameterType().toString());
//      log.info(returnType.getParameterType().equals(ResultVO.class) ? "yes" : "no");
//      return !returnType.getGenericParameterType().equals(ResultVO.class);
      return !returnType.getParameterType().equals(ResultVO.class);
   }

   @Override
   public Object beforeBodyWrite(Object data, MethodParameter returnType, MediaType mediaType,
                                 Class<? extends HttpMessageConverter<?>> aClass,
                                 ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
      // String类型不能直接包装，所以要进行些特别的处理
      if (returnType.getGenericParameterType().equals(String.class)) {
         ObjectMapper objectMapper = new ObjectMapper();
         try {
            // 将数据包装在ResultVO里后，再转换为json字符串响应给前端
            return objectMapper.writeValueAsString(new ResultVO<>(data));
         } catch (JsonProcessingException e) {
            throw new APIException();
         }
      }
      // 将原本的数据包装在ResultVO里
      return new ResultVO<>(data);
   }
}
// 注意直接返回String数据不能直接包装，所以需要额外处理
