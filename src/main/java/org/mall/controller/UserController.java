package org.mall.controller;

import lombok.extern.slf4j.Slf4j;
import org.mall.dto.UserLoginDTO;
import org.mall.dto.UserRegisterDTO;
import org.mall.service.UserService;
import org.mall.utils.ResultCode;
import org.mall.utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
   @Autowired
   private UserService userService;

   /**
    * 响应用户登录页面(如果用户已经登录, 则直接重定向到用户主页)
    * @return 用户登录页 templates/users/login.html
    */
   @GetMapping({"", "/", "/login", "/login/"})
   public String getLoginPage() {
      // TODO: 如果用户已经登录, 直接重定向到用户主页

      // 用户未登录, 返回登录页面
      return "users/login";
   }

   /**
    * 响应用户注册页面
    * @return 用户注册页 templates/users/register.html
    */
   @GetMapping("/register")
   public String getRegisterPage() {
      return "users/register";
   }

   /**
    * 处理用户注册请求(未指定格式, 可以是通用表单类型和json)
    *
    * @param userRegisterDTO 接收用户注册信息, @Valid注解校验注册信息格式,
    * @return 注册处理结果
    */
   @PostMapping(value = "/register")
   @ResponseBody
   public ResultVO<Object> doRegister(@Valid UserRegisterDTO userRegisterDTO) {
      // 参数校验通过, 可以注册
      return userService.register(userRegisterDTO);
   }

   /// 注:
   // (1) 上面的 doRegister 使用 @Valid 注解校验信息, 如果校验失败, 则会抛出 BindException,
   // 异常会在 org.mall.utils.GlobalExceptionHandler 中进行统一处理, 可以减少代码冗余.
   //
   // (2) 下面的 doLogin 是另一种处理校验失败的方式
   // 要求在 @Valid 注解的参数后面添加 BindingResult 参数, 校验结果将注入 BindingResult 中
   // 不会抛出 BindException 异常.

   /**
    * 处理用户登录请求
    *
    * @param userLoginDTO 接收用户登录信息, @Valid注解校验登录信息格式
    * @param request 用于获取session, 检验是否已经登录
    * @return 登录处理结果
    */
   @PostMapping("/login/{name}")
   @ResponseBody
   public ResultVO<Object> doLogin(@Valid UserLoginDTO userLoginDTO, BindingResult result, HttpServletRequest request) {
      // 不允许重复登录
      if (null != request.getSession().getAttribute("user_online")) {
         // TODO: 返回用户主页, 还是用户已登录呢???
         return new ResultVO<>(ResultCode.USER_ALREADY_LOGIN, null);
      }

      // 参数校验不通过, 不可登录
      if (result.hasErrors()) {
         // 获取错误信息并返回
         List<String> list = new ArrayList<>();
         for (ObjectError error : result.getAllErrors()) {
            list.add(error.getDefaultMessage());
         }
         return new ResultVO<>(ResultCode.VALIDATE_FAILED, list);
      }

      // 未登录并且参数校验通过, 可以登录
      return userService.login(userLoginDTO, request.getSession());
   }



   // https://blog.csdn.net/weixin_38004638/article/details/99655322
   // @RequestParam 与 @RequestBody
   // https://blog.csdn.net/zhoubingzb/article/details/88311624
   // 去掉@Valid
   // https://blog.csdn.net/q1035331653/article/details/80370818
   //
   // @Valid
   // 需要BindingResult 与 手动触发
   // https://www.jianshu.com/p/ce35092e89d2

   // 如果是@RestController + ModelAndView
   // 则要求请求url与返回的url不相同, 否则会报500错误

   // @ResponseBody 将结果对象转成Json返回
   // GET ...
   // @RequestParam(name = "phoneNo") String phoneNo

   // Model 和 HttpServletRequest/Response 只要提供形参, 就会直接注入
   // Model
   // model.addAttribute("user", user);
   // HttpServletRequest
   // request.getSession().setAttribute("user", user);

   // return "redirect:/idle/items";
}
