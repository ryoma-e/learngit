package org.mall.config;

import org.mall.utils.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoginConfig implements WebMvcConfigurer {
   @Override
   public void addInterceptors(InterceptorRegistry registry) {
      // 注册 LoginInterceptor 拦截器
      InterceptorRegistration registration = registry.addInterceptor(new LoginInterceptor());
//      registration.addPathPatterns("/**"); // 所有路径都被拦截
      registration.addPathPatterns("/admin/**");
   }
}
