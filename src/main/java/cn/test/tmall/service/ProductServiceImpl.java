package cn.test.tmall.service;

import cn.test.tmall.mapper.CategoryMapper;
import cn.test.tmall.mapper.ProductMapper;
import cn.test.tmall.pojo.Category;
import cn.test.tmall.pojo.Product;
import cn.test.tmall.pojo.ProductExample;
import cn.test.tmall.pojo.ProductImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    ProductImageService productImageService;

    @Autowired
    ProductService productService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    ReviewService reviewService;

    @Override
    public List<Product> list(int cid) {
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andCidEqualTo(cid);
        productExample.setOrderByClause("id desc");
        List<Product> products = productMapper.selectByExample(productExample);
        setCategory(products);
        setFirstProductImage(products);
        return products;
    }

    @Override
    public void add(Product product) {
        productMapper.insert(product);
    }

    @Override
    public void delete(int id) {
        productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Product product) {
        productMapper.updateByPrimaryKeySelective(product);
    }

    @Override
    public Product get(int id) {
        Product product = productMapper.selectByPrimaryKey(id);
        setFirstProductImage(product);
        setCategory(product);
        return product;
    }


    public void setCategory(Product product){
        Category category = categoryMapper.selectByPrimaryKey(product.getCid());
        product.setCategory(category);
    }
    public void setCategory(List<Product> products){
        for (Product product:products){
            setCategory(product);
        }
    }

    @Override
    public void setFirstProductImage(Product product) {
        List<ProductImage> productImages = productImageService.list(product.getId(),ProductImageService.type_single);
        if(!productImages.isEmpty()){
            ProductImage pi = productImages.get(0);
            product.setFirstProductImage(pi);
        }
    }

    @Override
    public void fill(Category category) {
        List<Product> products = list(category.getId());
        category.setProducts(products);
    }

    @Override
    public void fill(List<Category> categories) {
        for(Category category : categories){
            fill(category);
        }
    }

    @Override
    public void fillByRow(List<Category> categories) {
        int num = 8;
        for(Category category : categories){
            List<Product> products = category.getProducts();
            List<List<Product>> productsByRow = new ArrayList<>();
            for(int i = 0;i < products.size(); i+=num){
                int size = i + num;
                size = size > products.size() ? products.size() : size;
                List<Product> products1 = products.subList(i,size);
                productsByRow.add(products1);
            }
            category.setProductsByRow(productsByRow);
        }


    }

    public void setFirstProductImage(List<Product> ps){
        for(Product p : ps){
            setFirstProductImage(p);
        }
    }

    @Override
    public void setSaleAndReviewCount(Product product) {
        int salecount = orderItemService.getSaleCount(product.getId());
        product.setSale_count(salecount);

        int reviewcount = reviewService.getCount(product.getId());
        product.setReview_count(reviewcount);


    }

    @Override
    public void setSaleAndReviewCount(List<Product> products) {
        for(Product product : products){
            setSaleAndReviewCount(product);
        }
    }

    @Override
    public List<Product> search(String keyword) {
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andNameLike("%"+keyword+"%");
        productExample.setOrderByClause("id desc");
        List<Product> products = productMapper.selectByExample(productExample);
        setFirstProductImage(products);
        setCategory(products);
        return products;
    }
}
