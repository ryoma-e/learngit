package org.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@SpringBootApplication
@RestController
@MapperScan(basePackages = "org.mall.mapper")
public class MallApplication {
   @GetMapping({"", "/"})
   ModelAndView home() {
      return new ModelAndView("idles/items.html");
   }

   public static void main(String[] args) {
      SpringApplication.run(MallApplication.class, args);
   }
}
