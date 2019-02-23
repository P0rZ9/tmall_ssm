package cn.test.tmall.controller;

import cn.test.tmall.pojo.Category;
import cn.test.tmall.pojo.Property;
import cn.test.tmall.service.CategoryService;
import cn.test.tmall.service.PropertyService;
import cn.test.tmall.util.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class PropertyController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    PropertyService propertyService;

    @RequestMapping(value = "/admin_property_list",method = RequestMethod.GET)
    public String list(ModelMap modelMap, Page page, HttpServletRequest request){
        int cid = Integer.parseInt(request.getParameter("cid"));
        Category category = categoryService.get(cid);

        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Property> properties = propertyService.list(cid);
        int total = (int) new PageInfo<>(properties).getTotal();
        page.setTotal(total);
        page.setParam("&cid="+category.getId());


        modelMap.addAttribute("properties",properties);
        modelMap.addAttribute("page",page);
        modelMap.addAttribute("category",category);
        return "admin/list_property";
    }

    @RequestMapping(value = "/admin_property_add")
    public String add(HttpServletRequest request){
        int cid = Integer.parseInt(request.getParameter("cid"));
        String name = request.getParameter("name");
        Property c = new Property();
        c.setName(name);
        c.setCid(cid);
        propertyService.add(c);

        return "redirect:/admin_property_list?cid="+cid;
    }

    @RequestMapping(value = "admin_property_delete",method = RequestMethod.GET)
    public String delete(int id,HttpSession session){
        Property property = propertyService.get(id);
        propertyService.delete(id);
        return "redirect:/admin_property_list?cid="+property.getCid();
    }

    @RequestMapping(value = "admin_property_edit",method = RequestMethod.GET)
    public String edit(int id,ModelMap modelMap){
        Property property = propertyService.get(id);
        Category category = categoryService.get(property.getCid());

        property.setCategory(category);
        modelMap.addAttribute("property",property);
        return "admin/edit_property";
    }

    @RequestMapping(value = "admin_property_editP",method = RequestMethod.POST)
    public String editP(Property c) throws IOException {
        propertyService.update(c);
        return "redirect:/admin_property_list?cid="+c.getCid();
    }


}
