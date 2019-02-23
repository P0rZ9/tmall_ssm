package cn.test.tmall.service;
import cn.test.tmall.pojo.Product;
import cn.test.tmall.pojo.ProductImage;
import java.util.List;

public interface ProductImageService {

    public String type_single = "type_single";
    public String type_detail = "type_detail";
    List<ProductImage> list(int pid, String type);

    void add(ProductImage productImage);
    void delete(int id);
    void update(ProductImage productImage);
    ProductImage get(int id);

}
