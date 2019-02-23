package cn.test.tmall.service;

import cn.test.tmall.pojo.Category;
import cn.test.tmall.pojo.Product;
import cn.test.tmall.pojo.ProductImage;

import java.util.List;

public interface ProductService {
    List<Product> list(int cid);

    void add(Product product);
    void delete(int id);
    void update(Product product);
    Product get(int id);
    void setFirstProductImage(Product product);

    public void fill(Category category);
    public void fill(List<Category> categories);
    public void fillByRow(List<Category> categories);

    void setSaleAndReviewCount(Product product);
    void setSaleAndReviewCount(List<Product> products);

    List<Product> search(String keyword);
}
