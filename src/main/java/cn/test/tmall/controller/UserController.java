package cn.test.tmall.controller;

import cn.test.tmall.pojo.User;
import cn.test.tmall.service.UserService;
import cn.test.tmall.util.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller

public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/admin_user_list", method = RequestMethod.GET)
    public String list(ModelMap modelMap, Page page){
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<User> users = userService.list();
        int total = (int) new PageInfo<>(users).getTotal();
        page.setTotal(total);
        modelMap.addAttribute("page",page);
        modelMap.addAttribute("users",users);
        return "admin/list_user";
    }

    @RequestMapping(value = "/admin_user_add", method = RequestMethod.POST)
    public String add(User user){
        userService.add(user);
        return "redirect:admin_user_list";
    }

    @RequestMapping(value = "/admin_user_delete", method = RequestMethod.GET)
    public String delete(int id){
        userService.delete(id);
        return "redirect:admin_user_list";
    }

    @RequestMapping(value = "admin_user_edit", method = RequestMethod.GET)
    public String edit(int id,ModelMap modelMap){
        User user = userService.get(id);
        modelMap.addAttribute("user",user);
        return "admin/edit_user";
    }

    @RequestMapping(value = "admin_user_editP", method = RequestMethod.POST)
    public String editP(User user){
        userService.update(user);
        return "redirect:admin_user_list";
    }

    @RequestMapping(value = "admin_login_p",method = RequestMethod.POST)
    public String loginP(User user, ModelMap modelMap, HttpSession session){
        String name = HtmlUtils.htmlEscape(user.getName());
        String password = HtmlUtils.htmlEscape(user.getPassword());
        if(name.equals("admin") && password.equals("admin")){
            session.setAttribute("user",user);
            return "redirect:admin_category_list";
        }
        return "redirect:admin_login";


    }

    @RequestMapping(value = "admin_logout")
    public String logut(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin_login";
    }


}
