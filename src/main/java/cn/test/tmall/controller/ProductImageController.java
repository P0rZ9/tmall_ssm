package cn.test.tmall.controller;


import cn.test.tmall.pojo.Product;
import cn.test.tmall.pojo.ProductImage;
import cn.test.tmall.service.ProductImageService;
import cn.test.tmall.service.ProductService;
import cn.test.tmall.util.ImageUtil;
import cn.test.tmall.util.UploadedImageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

@Controller

public class ProductImageController {

    @Autowired
    ProductImageService productImageService;

    @Autowired
    ProductService productService;

    @RequestMapping(value = "/admin_product_image_list",method = RequestMethod.GET)
    public String list(int pid, String type, ModelMap modelMap){
        Product product = productService.get(pid);
        List<ProductImage> p_image_single = productImageService.list(pid,ProductImageService.type_single);
        List<ProductImage> p_image_detail = productImageService.list(pid,ProductImageService.type_detail);

        modelMap.addAttribute("product",product);
        modelMap.addAttribute("p_image_single",p_image_single);
        modelMap.addAttribute("p_image_detail",p_image_detail);
        return "admin/list_product_image";
        }

    @RequestMapping(value = "/admin_product_image_add",method = RequestMethod.POST)
    public String add(ProductImage  pi, HttpSession session, UploadedImageFile uploadedImageFile) {
        productImageService.add(pi);
        String fileName = pi.getId()+ ".jpg";
        String imageFolder;
        String imageFolder_small=null;
        String imageFolder_middle=null;
        if(ProductImageService.type_single.equals(pi.getType())){
            imageFolder= session.getServletContext().getRealPath("img/productSingle");
            imageFolder_small= session.getServletContext().getRealPath("img/productSingle_small");
            imageFolder_middle= session.getServletContext().getRealPath("img/productSingle_middle");
        }
        else{
            imageFolder= session.getServletContext().getRealPath("img/productDetail");
        }

        File f = new File(imageFolder, fileName);
        f.getParentFile().mkdirs();
        try {
            uploadedImageFile.getImage().transferTo(f);

            //把上传的数据格式转化为jpg,不仅仅是后缀名为.jpg
            BufferedImage img = ImageUtil.change2jpg(f);
            ImageIO.write(img, "jpg", f);

            if(ProductImageService.type_single.equals(pi.getType())) {
                File f_small = new File(imageFolder_small, fileName);
                File f_middle = new File(imageFolder_middle, fileName);

                //把正常大小的图片复制到2个目录下
                ImageUtil.resizeImage(f, 56, 56, f_small);
                ImageUtil.resizeImage(f, 217, 190, f_middle);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:admin_product_image_list?pid="+pi.getPid();
    }

    @RequestMapping(value = "/admin_product_image_delete", method = RequestMethod.GET)
    public String delete(int id, ModelMap modelMap, HttpSession session){
        ProductImage productImage = productImageService.get(id);

        String Filename = productImage.getId()+".jpg";
        String imageFolder;
        String imageFolder_small = null;
        String imageFolder_middle = null;

        if(ProductImageService.type_single.equals(productImage.getType())){
            imageFolder = session.getServletContext().getRealPath("img/productSingle");
            imageFolder_small = session.getServletContext().getRealPath("img/productSingle_small");
            imageFolder_middle = session.getServletContext().getRealPath("img/productSingle_middle");

            File imageFile = new File(imageFolder,Filename);
            File file_small = new File(imageFolder_small,Filename);
            File file_middle = new File(imageFolder_middle,Filename);

            imageFile.delete();
            file_small.delete();
            file_middle.delete();
        }else{
            imageFolder = session.getServletContext().getRealPath("img/productDetail");
            File imageFile = new File(imageFolder,Filename);
            imageFile.delete();
        }

        productImageService.delete(id);
        return "redirect:admin_product_image_list?pid="+productImage.getPid();

    }


    }


