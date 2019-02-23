package cn.test.tmall.controller;

import cn.test.tmall.pojo.Category;
import cn.test.tmall.service.CategoryService;
import cn.test.tmall.util.Page;
import cn.test.tmall.util.UploadedImageFile;
import cn.test.tmall.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller

public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @RequestMapping(value = "/admin_category_list",method = RequestMethod.GET)
    public String list(ModelMap modelMap, Page page){
        List<Category> categories = categoryService.list_page(page);
        int total = categoryService.total();
        page.setTotal(total);
        modelMap.addAttribute("categories",categories);
        modelMap.addAttribute("page",page);
        return "admin/list_category";
    }

    @RequestMapping(value = "/admin_category_add")
    public String add(HttpServletRequest request, HttpSession session, UploadedImageFile uploadedImageFile) throws IOException {
        String name = request.getParameter("name");
        Category c = new Category();
        c.setName(name);
        categoryService.add(c);

        //获取图片存储路径
        File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder,c.getId()+".jpg");
        if(!file.getParentFile().exists())
            file.getParentFile().mkdir();
        uploadedImageFile.getImage().transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img,"jpg",file);
        return "redirect:/admin_category_list";
    }

    @RequestMapping(value = "/admin_category_delete",method = RequestMethod.GET)
    public String delete(int id,HttpSession session){
        categoryService.delete(id);
        File imageFolder = new File(session.getServletContext().getRealPath("ima/category"));
        File file = new File(imageFolder,id+".jpg");
        file.delete();
        return "redirect:/admin_category_list";
    }

    @RequestMapping(value = "/admin_category_edit",method = RequestMethod.GET)
    public String edit(int id,ModelMap modelMap){
        Category category = categoryService.get(id);
        modelMap.addAttribute("category",category);
        return "admin/edit_category";
    }

    @RequestMapping(value = "/admin_category_editP",method = RequestMethod.POST)
    public String editP(Category c,ModelMap modelMap,UploadedImageFile uploadedImageFile,HttpSession session) throws IOException {
        categoryService.update(c);
        MultipartFile image = uploadedImageFile.getImage();
        if(null!=image &&!image.isEmpty()){
            File  imageFolder= new File(session.getServletContext().getRealPath("img/category"));
            File file = new File(imageFolder,c.getId()+".jpg");
            image.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
        }
        return "redirect:/admin_category_list";
    }




}