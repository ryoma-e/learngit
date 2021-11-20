package org.mall.utils;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录拦截器
 *
 * 通过实现 HandlerInterceptor 接口来定义拦截器
 * 可以选择实现3个方法: preHandle, postHandle, afterCompletion
 *
 * @author xu wenbo
 * @see org.mall.config.LoginConfig
 */
public class LoginInterceptor implements HandlerInterceptor {
   /**
    * 在请求之前调用(Controller访求调用之前), 拦截器功能主要在这个部分实现
    *
    * 通过查询session是否存在user_online属性验证用户是否登录
    * 如果用户未登录, 则重定向到登录界面
    *
    * 前置条件: 用户登录成功后, 需要在session写入`user_online`属性
    * 后置条件: 实现 WebMvcConfigurer 接口(@Configuration) 的配置类, 注册该拦截器并设置拦截路径
    */
   @Override
   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
      try {
         HttpSession session = request.getSession();
         // 统一拦截, 查询session是否存在user_online属性
         Object obj = session.getAttribute("user_online");
         if (obj != null) {
            return true;
         }
         // TODO: 管理员和普通用户要分开处理
         // 普通用户
         System.out.println("path: "+request.getContextPath());
         response.sendRedirect(request.getContextPath() + "/user/login");
      } catch (Exception e) {
         e.printStackTrace();
      }
      return false;
   }

//   /**
//    * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
//    * 可以对返回的ModelAndView进行处理
//    */
//   @Override
//   public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//   }

//   /**
//    * 整个请求结束之后被调用，也就是在DispatchServlet渲染了对应的视图之后执行（主要用于进行资源清理工作）
//    */
//   @Override
//   public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//   }
}
