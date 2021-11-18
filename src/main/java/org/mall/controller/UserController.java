package org.mall.controller;

import lombok.extern.slf4j.Slf4j;
import org.mall.dto.UserLoginDTO;
import org.mall.dto.UserOnlineDTO;
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

   @GetMapping({"", "/", "/login", "/login/"})
   public String getLoginPage() {
      return "users/login";
   }

   @GetMapping("/register")
   public String getRegisterPage() {
      return "users/register";
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

   @PostMapping(value = "/register")
   @ResponseBody
   public ResultVO<Object> doRegister(@Valid UserRegisterDTO userRegisterDTO, BindingResult result) {
      if (result.hasErrors()) {
         List<String> list = new ArrayList<>();
         for (ObjectError error : result.getAllErrors()) {
            list.add(error.getDefaultMessage());
         }
         return new ResultVO<>(ResultCode.VALIDATE_FAILED, list);
      }
      // TODO: TooManyResultsException phoneNo冲突
      return userService.register(userRegisterDTO);
   }

   @PostMapping("/login")
   @ResponseBody
   public ResultVO<Object> doLogin(UserLoginDTO userLoginDTO, HttpServletRequest request) {
      // TODO: 不允许重复登录
      if (null != request.getSession().getAttribute("user_online")) {
         return new ResultVO<>(ResultCode.USER_ALREADY_LOGIN, null);
      }

      UserOnlineDTO userOnLineDTO = userService.findUser(userLoginDTO.getPhoneNo());
      if (userOnLineDTO == null) {
         return new ResultVO<>(ResultCode.USER_NOT_EXIST, null);
      }
      boolean res = userService.checkPassword(userLoginDTO);
      if (!res) {
         return new ResultVO<>(ResultCode.USER_LOGIN_FAILED, null);
      }
      request.getSession().setAttribute("user_online", userOnLineDTO);
      return new ResultVO<>(userOnLineDTO);
   }

   // 如果是@RestController + ModelAndView
   // 则要求请求url与返回的url不相同, 否则会报500错误

//   @PostMapping("/register")
//   // @ResponseBody
//   public String doRegister(@Valid UserRegisterDTO userRegisterDTO, Model model, HttpServletRequest request) {
//      boolean res = userService.register(userRegisterDTO);
//      if (res) {
//         model.addAttribute("user", user);
//         request.getSession().setAttribute("user", user);
//         return "redirect:/idle/items";
//      }
//      return "users/failed";
//   }

//   /**
//    * 处理用户登录请求, 请求内容包括手机号和密码
//    * @return (待定)
//    */
//   @PostMapping("/login")
//   public String doLogin(@RequestParam(name = "phoneNo") String phoneNo,
//                         @RequestParam(name = "password") String password,
//                         Model model, HttpServletRequest request) {
//      if (password == null
//              || phoneNo == null
//              || !phoneNo.matches("1[0-9]{10}")
//              || !userService.checkPassword(phoneNo, password)) {
//         return "users/failed";
//      }
//      User user = userService.getUserByPhoneNo(phoneNo);
//      model.addAttribute("user", user);
//      request.getSession().setAttribute("user", user);
//      return "redirect:/idle/items";
//   }

   // Model 和 HttpServletRequest/Response 只要提供形参, 就会直接注入
}
