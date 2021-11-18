package org.mall.controller;

import com.fasterxml.jackson.core.JsonEncoding;
import org.apache.logging.log4j.message.ReusableMessage;
import org.mall.entity.User;
import org.mall.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
   @Autowired
   private AdminService adminService;

   @GetMapping({"", "/", "/consoles"})
   public String consoles() {
      return "admins/consoles";
   }

   @GetMapping("/users")
   public String getUsers(Model model) {
      // TODO: List<AdminUsersListItemDTO>
      model.addAttribute("users", adminService.findAllUsers());
      return "admins/users";
   }

   @GetMapping("/users/registrations")
   public String getRegistrations(Model model) {
      // TODO: List<AdminRegistrationsListItemDTO>
      model.addAttribute("registrations", adminService.findAllAccountByStatus(1));
      return "admins/registrations";
   }

   @GetMapping("/users/registrations/{uid}/info")
   public String getRegistrationsInfo(@PathVariable String uid) {
      // TODO: AdminRegistrationsListItemDTO
      return null;
   }

   @PostMapping("/users/registrations/{uid}/{option}")
   @ResponseBody
   public String doRegistrationsCheck(@PathVariable String uid, @PathVariable boolean option) {
//      return adminService.updateAccountStatus(option ? 3 : 2);
      return null;
   }

//   @GetMapping("/user/{uid}")
//   @ResponseBody
//   public String getUser(@PathVariable String uid) {
//      return adminService.getUser(uid);
//   }
//
//   @DeleteMapping("/user/{uid}")
//   @ResponseBody
//   public int delUserById(@PathVariable String uid) {
//      return adminService.delUserById(uid);
//   }
//
//   @PostMapping("/user")
//   @ResponseBody
//   public int addUser(User user) {
//      return adminService.addUser(user);
//   }
}
