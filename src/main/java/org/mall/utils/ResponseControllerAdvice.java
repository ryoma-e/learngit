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

/**
 * 全局处理增强版 Controller
 * 处理未使用ResultVO包装的响应内容(这里主要是String类型的, 不能直接通过@ResponseBody转成json)
 *
 * @author Xu Wenbo
 * @see ResultVO
 */
@RestControllerAdvice(basePackages = {"org.mall.controller"})
@Slf4j
public class ResponseControllerAdvice implements ResponseBodyAdvice<Object> {
   @Override
   public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> aClass) {
      // 如果接口返回的类型本身就是ResultVO那就没有必要进行额外的操作, 返回false
      return !returnType.getParameterType().equals(ResultVO.class);

      // Generic修饰的方法返回的class描述有所不同, 涉及泛型类时需要注意
      //return !returnType.getGenericParameterType().equals(ResultVO.class);
   }

   @Override
   public Object beforeBodyWrite(Object data, MethodParameter returnType, MediaType mediaType,
                                 Class<? extends HttpMessageConverter<?>> aClass,
                                 ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
      // String类型不能直接包装成JSON数据(json格式示例: "name": "value"; String类型没有"name"), 所以要进行些特别的处理
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
