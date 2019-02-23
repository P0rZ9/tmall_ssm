package cn.test.tmall.service;
import cn.test.tmall.mapper.CategoryMapper;
import cn.test.tmall.pojo.Category;
import cn.test.tmall.pojo.CategoryExample;
import cn.test.tmall.service.CategoryService;
import cn.test.tmall.util.Page;
import com.github.pagehelper.page.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;
    //自动装配



    public List<Category> list() {
        CategoryExample categoryExample = new CategoryExample();
        List<Category> categories = categoryMapper.selectByExample(categoryExample);
        return categories;
    }

    public List<Category> list_page(Page page) {

        return categoryMapper.list_in_page(page);
    }

    @Override
    public int total() {
        return categoryMapper.total();
    }


    @Override
    public void add(Category category) {
        categoryMapper.insert(category);
    }

    @Override
    public Category get(int id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public void delete(int id) {
        categoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Category category) {
        categoryMapper.updateByPrimaryKeySelective(category);
    }
}