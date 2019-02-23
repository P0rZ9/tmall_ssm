package cn.test.tmall.service;

import cn.test.tmall.mapper.PropertyMapper;
import cn.test.tmall.pojo.Category;
import cn.test.tmall.pojo.CategoryExample;
import cn.test.tmall.pojo.Property;
import cn.test.tmall.pojo.PropertyExample;
import cn.test.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    PropertyMapper propertyMapper;

    @Override
    public List<Property> list(int cid) {
        PropertyExample propertyExample = new PropertyExample();
        propertyExample.createCriteria().andCidEqualTo(cid);
        propertyExample.setOrderByClause("id desc");
        return propertyMapper.selectByExample(propertyExample);

    }

    @Override
    public void add(Property property) {
        propertyMapper.insert(property);
    }

    @Override
    public void delete(int id) {
        propertyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Property property) {
        propertyMapper.updateByPrimaryKeySelective(property);
    }

    @Override
    public Property get(int id) {
        Property property = propertyMapper.selectByPrimaryKey(id);
        return property;
    }

    @Override
    public int total() {
        return propertyMapper.total();
    }
}
