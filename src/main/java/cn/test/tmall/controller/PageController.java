package cn.test.tmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
//控制普通页面的显示
public class PageController {

    @RequestMapping("register_page")
    public String registerPage(ModelMap modelMap){
        return "fore/register";
    }

    @RequestMapping(value = "register_success")
    public String register_success(ModelMap modelMap){
        return "fore/register_success";
    }

    @RequestMapping(value = "login_page")
    public String login(ModelMap modelMap){
        return "fore/login";
    }

    @RequestMapping(value = "fore_alipay")
    public String alipay(){
        return "fore/alipay";
    }

    @RequestMapping(value = "admin_login")
    public String adminlogin(){
        return "admin/login";
    }


}
