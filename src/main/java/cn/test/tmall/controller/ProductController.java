package cn.test.tmall.controller;


import cn.test.tmall.pojo.Category;
import cn.test.tmall.pojo.Product;
import cn.test.tmall.service.CategoryService;
import cn.test.tmall.service.ProductService;
import cn.test.tmall.util.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;


    @RequestMapping(value = "/admin_product_list",method = RequestMethod.GET)
    public String list(ModelMap modelMap, Page page, HttpServletRequest request){
        int cid = Integer.parseInt(request.getParameter("cid"));
        Category category = categoryService.get(cid);


        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Product> products = productService.list(cid);

        int total = (int) new PageInfo<>(products).getTotal();
        page.setTotal(total);
        page.setParam("&cid="+category.getId());

        modelMap.addAttribute("products",products);
        modelMap.addAttribute("page",page);
        modelMap.addAttribute("category",category);
        return "admin/list_product";
    }


    @RequestMapping(value = "/admin_product_add")
    public String add(Product product) {
        productService.add(product);
        return "redirect:/admin_product_list?cid="+product.getCid();
    }

    @RequestMapping(value = "/admin_product_delete",method = RequestMethod.GET)
    public String delete(int id){
        Product product = productService.get(id);
        productService.delete(id);
        return "redirect:/admin_product_list?cid="+product.getCid();
    }

    @RequestMapping(value = "/admin_product_edit",method = RequestMethod.GET)
    public String edit(int id,ModelMap modelMap){
        Product product = productService.get(id);
        modelMap.addAttribute("product",product);
        return "admin/edit_product";
    }

    @RequestMapping(value = "/admin_product_editP",method = RequestMethod.POST)
    public String editP(Product product){
        productService.update(product);
        return "redirect:/admin_product_list?cid="+product.getCid();
    }


}
