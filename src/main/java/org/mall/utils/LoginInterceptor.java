package org.mall.utils;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
   /**
    * 在请求之前调用(Controller访求调用之前), 验证用户是否登录
    * 如果用户未登录, 则重定向到登录界面
    */
   @Override
   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
      try {
         HttpSession session = request.getSession();
         // 统一拦截（查询当前session是否存在user）(这里user会在每次登录成功后，写入session)
         Object obj = session.getAttribute("user_online");
         if (obj != null) {
            return true;
         }
         System.out.println("path: "+request.getContextPath());
         response.sendRedirect(request.getContextPath() + "/user/login");
      } catch (Exception e) {
         e.printStackTrace();
      }
      return false;
      //如果返回值为false时，被请求时，拦截器执行到此处将不会继续操作
      //如果返回值为true时，请求将会继续执行后面的操作
   }
}
