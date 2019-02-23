package cn.test.tmall.mapper;

import cn.test.tmall.pojo.Property;
import cn.test.tmall.pojo.PropertyExample;
import cn.test.tmall.util.Page;

import java.util.List;

public interface PropertyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Property record);

    int insertSelective(Property record);

    List<Property> selectByExample(PropertyExample example);

    Property selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Property record);

    int updateByPrimaryKey(Property record);

    List<Property> list(Page page);

    int total();

}