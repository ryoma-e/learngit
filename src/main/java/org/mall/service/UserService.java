package org.mall.service;

import org.mall.dto.UserLoginDTO;
import org.mall.dto.UserOnlineDTO;
import org.mall.dto.UserRegisterDTO;
import org.mall.utils.DTOConverters;
import org.mall.entity.User;
import org.mall.mapper.UserMapper;
import org.mall.utils.PasswordEncrypt;
import org.mall.utils.ResultCode;
import org.mall.utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class UserService {
   @Autowired
   UserMapper userMapper;

   /**
    * 用户注册处理
    * @param userRegisterDTO 用户提交的注册信息(要求参数格式正确)
    * @return 用户注册结果
    */
   public ResultVO<Object> register(UserRegisterDTO userRegisterDTO) {
      // 转换数据
      User user = DTOConverters.convert2User(userRegisterDTO);

      // 尝试录入注册信息
      int res = userMapper.addUser(user);
      // 录入失败
      if (res != 1) {
         // TODO: 解决各种问题, 包括TooManyResultsException phoneNo冲突
         return new ResultVO<>(ResultCode.ERROR, null);
      }

      // 录入成功, 获取用户信息(uid是数据库触发器在用户注册信息插入时生成的)
      user = userMapper.getUserByPhoneNo(user.getPhoneNo());

      // 只提取需要的数据(uid, nickname, userImageUrl)并返回
      UserOnlineDTO userOnLineDTO = DTOConverters.convert2UserOnlineDTO(user);
      return new ResultVO<>(userOnLineDTO);
   }

   /**
    * 用户登录处理
    * @param userLoginDTO 用户提交的登录信息(要求参数格式正确)
    * @param httpSession 用户设置
    * @return 用户登录结果
    */
   public ResultVO<Object> login(UserLoginDTO userLoginDTO, HttpSession httpSession) {
      // 校验用户信息是否存在
      User user = userMapper.getUserByPhoneNo(userLoginDTO.getPhoneNo());
      if (user == null) { // 用户不存在
         return new ResultVO<>(ResultCode.USER_NOT_EXIST, null);
      }

      // 校验用户密码是否正确
      String userPassword = user.getPassword();
      if (userPassword == null ||
              !userPassword.equals(PasswordEncrypt.encrypt(userLoginDTO.getPassword()))) {
         return new ResultVO<>(ResultCode.USER_LOGIN_FAILED, null);
      }

      // 提取需要的信息, 并记录登录状态
      UserOnlineDTO userOnlineDTO = DTOConverters.convert2UserOnlineDTO(user);
      httpSession.setAttribute("user_online", userOnlineDTO);

      return new ResultVO<>(userOnlineDTO);
   }

//   public UserOnlineDTO findUser(String phoneNo) {
//      User user = userMapper.getUserByPhoneNo(phoneNo);
//      if (user == null) return null;
//      return DTOConverters.convert2UserOnlineDTO(user);
//   }

//   public boolean checkPassword(UserLoginDTO userLoginDTO) {
//      String password1 = userMapper.getPasswordByPhoneNo(userLoginDTO.getPhoneNo());
//      return password1 != null && password1.equals(PasswordEncrypt.encrypt(userLoginDTO.getPassword()));
//   }

//   public User getUserByPhoneNo(String phoneNo) {
//      return userMapper.getUserByPhoneNo(phoneNo);
//   }
}
