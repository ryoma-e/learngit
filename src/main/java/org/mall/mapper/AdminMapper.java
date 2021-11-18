package org.mall.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.mall.entity.User;

import java.util.List;

public interface AdminMapper extends UserMapper {
   @Update("UPDATE users SET accountStatus=#{status} WHERE uid=#{uid}")
   int updateUserStatus(String uid, int status);

   @Select("SELECT uid, nickname, password, accountStatus, userImageUrl, phoneNo, userName, schoolCardID, gender, wechatID, email, registerTime FROM users WHERE accountStatus=#{status}")
   List<User> getAllUsersByStatus(int status);
}
