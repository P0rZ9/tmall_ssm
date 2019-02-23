package cn.test.tmall.mapper;

import cn.test.tmall.pojo.Category;
import cn.test.tmall.pojo.CategoryExample;
import cn.test.tmall.util.Page;

import java.util.List;

public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    List<Category> selectByExample(CategoryExample example);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    public List<Category> list_in_page(Page page);

    public int total();
}