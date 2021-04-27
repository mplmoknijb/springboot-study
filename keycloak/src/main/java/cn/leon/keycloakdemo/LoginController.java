package cn.leon.keycloakdemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created on 2018/1/25 0025.
 *
 * @author zlf
 * @email i@merryyou.cn
 * @since 1.0
 */
@RestController
public class LoginController {

    @GetMapping("/token")
    public String require() {
        return "success";
    }

    @GetMapping("/signout")
    public String signout() {
        return "signout";
    }
}
