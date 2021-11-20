package org.mall.config;

import org.mall.utils.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * SpringBoot 注入拦截器配置(包括拦截器注册和拦截路径的设置)
 *
 * 原理:
 * SpringBoot通过实现 HandlerInterceptor 接口实现拦截器,
 * 通过实现 WebMvcConfigurer 接口实现一个配置类,
 * 在配置类中注入拦截器, 最后再通过 @Configuration 注解注入配置
 */
@Configuration
public class LoginConfig implements WebMvcConfigurer {
   @Override
   public void addInterceptors(InterceptorRegistry registry) {
      // 注册 LoginInterceptor 拦截器
      InterceptorRegistration registration = registry.addInterceptor(new LoginInterceptor());

      // 添加拦截路径
      // 示例: 拦截所有路径
      //registration.addPathPatterns("/**");

      // 管理员操作都需要登录验证（但不包括管理员登录和注册界面）
      registration.addPathPatterns("/admin/**");

      // TODO: 添加拦截路径

      // 添加不拦截的路径
      registration.excludePathPatterns(
              "/*/login",        // 登录路径
              "/*/register",     // 注册路径
              "/**/*.html",      // html静态资源
              "/**/*.css",       // css静态资源
              "/**/*.js"         // js静态资源
      );
   }
}
