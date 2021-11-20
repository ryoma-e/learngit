package org.mall.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.mall.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface UserMapper {
   @Select("SELECT uid, nickname, password, accountStatus, userImageUrl, phoneNo, userName, schoolCardID, gender, wechatID, email, registerTime FROM users")
   @Results(id = "userMap", value = {
           @Result(property = "uid", column = "uid", jdbcType = JdbcType.CHAR, id = true),
           @Result(property = "nickname", column = "nickname", jdbcType = JdbcType.VARCHAR),
           @Result(property = "password", column = "password", jdbcType = JdbcType.VARCHAR),
           @Result(property = "accountStatus", column = "accountStatus", jdbcType = JdbcType.INTEGER),
           @Result(property = "userImageUrl", column = "userImageUrl", jdbcType = JdbcType.VARCHAR),
           @Result(property = "phoneNo", column = "phoneNo", jdbcType = JdbcType.CHAR),
           @Result(property = "userName", column = "userName", jdbcType = JdbcType.VARCHAR),
           @Result(property = "schoolCardID", column = "schoolCardID", jdbcType = JdbcType.VARCHAR),
           @Result(property = "gender", column = "gender", jdbcType = JdbcType.INTEGER),
           @Result(property = "wechatID", column = "wechatID", jdbcType = JdbcType.VARCHAR),
           @Result(property = "email", column = "email", jdbcType = JdbcType.VARCHAR),
           @Result(property = "registerTime", column = "registerTime", jdbcType = JdbcType.DATETIMEOFFSET)
   })
   List<User> getAllUsers();

   @Select("SELECT uid, nickname, password, accountStatus, userImageUrl, phoneNo, userName, schoolCardID, gender, wechatID, email, registerTime FROM users WHERE uid=#{uid}")
   @ResultMap(value = "userMap")
   User getUserByUid(String uid);

   @Select("SELECT uid, nickname, password, accountStatus, userImageUrl, phoneNo, userName, schoolCardID, gender, wechatID, email, registerTime FROM users WHERE phoneNo=#{phoneNo}")
   @ResultMap(value = "userMap")
   User getUserByPhoneNo(String phoneNo);

   @Insert("INSERT INTO users (nickname, password, phoneNo, schoolCardID, userName, wechatID, email)" +
           " VALUES(#{nickname}, #{password}, #{phoneNo}, #{schoolCardID}, #{userName}, #{wechatID}, #{email})")
   int addUser(User user);

//   @Options(keyProperty = "uid", useGeneratedKeys = true)

//   @Select("SELECT password FROM users WHERE phoneNo=#{phoneNo}")
//   @Result(property = "password", column = "password", jdbcType = JdbcType.CHAR)
//   String getPasswordByPhoneNo(String phoneNo);

//   @Delete("DELETE FROM users WHERE uid=#{uid}")
//   int deleteUserByUid(String uid);
}
