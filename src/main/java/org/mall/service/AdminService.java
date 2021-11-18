package org.mall.service;

import org.mall.entity.User;
import org.mall.mapper.AdminMapper;
import org.mall.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
   @Autowired
   private AdminMapper adminMapper;

   public String getUser(String uid) {
      User user = adminMapper.getUserByUid(uid);
      return user != null ? user.toString() : "Error: No such user!";
   }

   public int delUserById(String uid) {
      return adminMapper.delUserById(uid);
   }

   public int addUser(User user) {
      user.setAccountStatus(1); // 新用户: 待审核
      return adminMapper.addUser(user);
   }

   public List<User> findAllUsers() {
      return adminMapper.getAllUsers();
   }

   public List<User> findAllAccountByStatus(int status) {
      return adminMapper.getAllUsersByStatus(status);
   }
}
