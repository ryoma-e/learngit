package org.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 项目: 校园咸鱼
 *
 *
 * 统一返回接口(JSON数据)
 * @see org.mall.utils.ResultVO
 * @see org.mall.utils.ResultCode
 * @see org.mall.utils.ResponseControllerAdvice
 *
 * 统一异常处理
 * @see org.mall.utils.APIException
 * @see org.mall.utils.GlobalExceptionHandler
 *
 * 参考博客:
 * Springboot统一返回接口+统一异常处理+后端参数校验 = https://cloud.tencent.com/developer/article/1699840
 *
 *
 * TODO: 登录校验校验(拦截器+session检测)
 * (一些页面需要登录才能进行操作)
 * @see org.mall.utils.LoginInterceptor
 * @see org.mall.config.LoginConfig
 *
 * 参考博客:
 * SpringBoot实现登录拦截器(实战版) = https://cloud.tencent.com/developer/article/1860615
 *
 *
 * 参数校验(validation, @Valid, BindResult)
 * 在DTO对象上添加相关注解进行校验, 参见 org.mall.dto.*
 * 部分简单请求(参数少的, 如GET请求, 手动进行校验)
 * TODO: 数据库操作结果的校验(唯一性冲突, 如手机号; 等等)
 *
 *
 * TODO: 通用用户事务:
 * @see org.mall.controller.UserController
 * @see org.mall.service.UserService
 * @see org.mall.mapper.UserMapper
 * @see org.mall.entity.User
 *
 *
 * TODO: 管理员事务:
 * @see org.mall.controller.AdminController
 * @see org.mall.service.AdminService
 * @see org.mall.mapper.AdminMapper
 *
 *
 * 日志, 使用 @Slf4j 注解进行配置
 *
 *
 * @author Xu Wenbo
 * @author Gou Boliang
 * @author Chen Handong
 * @author Feng Minghao
 * @author Chen Chaojie
 * @author Liu Wanquan
 * @author Deng Changjun
 */
@SpringBootApplication
@RestController
@MapperScan(basePackages = "org.mall.mapper")   // MyBatis
public class MallApplication {
   /**
    * 首页
    */
   @GetMapping({"", "/"})
   ModelAndView home() {
      // TODO: 设置主页
      // 临时主页
      return new ModelAndView("idles/items.html");
   }

   public static void main(String[] args) {
      SpringApplication.run(MallApplication.class, args);
   }
}
