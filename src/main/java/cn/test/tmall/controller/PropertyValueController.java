package cn.test.tmall.controller;


import cn.test.tmall.pojo.Product;
import cn.test.tmall.pojo.PropertyValue;
import cn.test.tmall.service.ProductService;
import cn.test.tmall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller

public class PropertyValueController {

    @Autowired
    ProductService productService;

    @Autowired
    PropertyValueService propertyValueService;

    @RequestMapping(value = "admin_property_value_edit",method = RequestMethod.GET)
    public String edit(ModelMap modelMap,int pid){
        Product product = productService.get(pid);

        //新建product 自动继承其分类的属性
        propertyValueService.init(product);
        List<PropertyValue> propertyValues = propertyValueService.list(product.getId());
        modelMap.addAttribute("propertyValues",propertyValues);
        modelMap.addAttribute("product",product);

        return "admin/edit_property_value";
    }

    @RequestMapping(value = "admin_property_value_editP",method = RequestMethod.POST)
    @ResponseBody
    public String editP(ModelMap modelMap,PropertyValue propertyValue){
        propertyValueService.update(propertyValue);
        return "success";
    }

}
