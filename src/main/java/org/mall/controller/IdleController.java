package org.mall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/idle")
public class IdleController {
   @RequestMapping({"", "/", "/items", "/items/"})
   public String getItems() {
      return "idles/items";
   }
}
