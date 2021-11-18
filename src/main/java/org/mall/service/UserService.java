package org.mall.service;

import org.mall.dto.UserLoginDTO;
import org.mall.dto.UserOnlineDTO;
import org.mall.dto.UserRegisterDTO;
import org.mall.dto.convert.UserConvert2DTO;
import org.mall.entity.User;
import org.mall.mapper.UserMapper;
import org.mall.utils.PasswordEncrypt;
import org.mall.utils.ResultCode;
import org.mall.utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class UserService {
   @Autowired
   UserMapper userMapper;

   public ResultVO<Object> register(UserRegisterDTO userRegisterDTO) {
      User user = User.builder()
              .nickname(userRegisterDTO.getNickname())
              .password(userRegisterDTO.getPassword())
              .phoneNo(userRegisterDTO.getPhoneNo())
              .userName(userRegisterDTO.getUserName())
              .schoolCardID(userRegisterDTO.getSchoolCardID())
              .wechatID(userRegisterDTO.getWechatID())
              .email(userRegisterDTO.getEmail())
              .build();
      int res = userMapper.addUser(user);
      if (res != 1) {
         return new ResultVO<>(ResultCode.ERROR, null);
      }
      user = userMapper.getUserByPhoneNo(user.getPhoneNo());
      UserOnlineDTO userOnLineDTO = UserConvert2DTO.convert2UserOnlineDTO(user);
      return new ResultVO<>(userOnLineDTO);
   }

   public UserOnlineDTO findUser(String phoneNo) {
      User user = userMapper.getUserByPhoneNo(phoneNo);
      if (user == null) return null;
      return UserConvert2DTO.convert2UserOnlineDTO(user);
   }

   public boolean checkPassword(UserLoginDTO userLoginDTO) {
      String password1 = userMapper.getPasswordByPhoneNo(userLoginDTO.getPhoneNo());
      return password1 != null && password1.equals(PasswordEncrypt.encrypt(userLoginDTO.getPassword()));
   }

   /**
    * nickname: 60个字节以内
    * password: 32位以内(ASCII)
    * phoneNo: 11个数字(1开头)
    * schoolCardID: 6位数字
    * userName: 60个字节以内
    * wechatID: 20个字节以内
    * email: 60个字节以内
    *
    * accountStatus: 默认为 0x1 待审核帐号
    * uid: 在插入时通过数据库的触发器自动造成
    */
   public boolean register(User user) {
      String nickname = user.getNickname();
      if (nickname == null || nickname.getBytes(StandardCharsets.UTF_8).length > 30) return false;

      String password = user.getPassword();
      // 任何非空白字符或空格
      if (password == null || !password.matches("[ \\S]{6,32}")) return false;

      String phoneNo = user.getPhoneNo();
      if (phoneNo == null || !phoneNo.matches("1[0-9]{10}")) return false;

      String schoolCardID = user.getSchoolCardID();
      if (schoolCardID == null || !schoolCardID.matches("[0-9]{6}")) return false;

      String userName = user.getUserName();
      if (userName == null || !(userName.getBytes().length < 60)) return false;

      // 密码加密
      user.setPassword(PasswordEncrypt.encrypt(password));

      int res = userMapper.addUser(user);
      return res == 1;
   }

   public boolean checkPassword(String phoneNo, String password) {
      String password1 = userMapper.getPasswordByPhoneNo(phoneNo);
      return password1 != null && password1.equals(PasswordEncrypt.encrypt(password));
   }

   public User getUserByPhoneNo(String phoneNo) {
      return userMapper.getUserByPhoneNo(phoneNo);
   }
}
