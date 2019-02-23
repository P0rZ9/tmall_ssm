package cn.test.tmall.service;

import cn.test.tmall.pojo.Category;
import cn.test.tmall.util.Page;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CategoryService {

    /**
     * 返回分类列表
     * @return
     */
    List<Category> list();


    List<Category> list_page(Page page);

    void add(Category category);

    Category get(int id);

    void delete(int id);

    void update(Category category);

    int total();


    // Category get(Integer id);

    //void update(Category category);

}