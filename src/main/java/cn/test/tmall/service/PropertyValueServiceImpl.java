package cn.test.tmall.service;


import cn.test.tmall.mapper.PropertyValueMapper;
import cn.test.tmall.pojo.Product;
import cn.test.tmall.pojo.Property;
import cn.test.tmall.pojo.PropertyValue;
import cn.test.tmall.pojo.PropertyValueExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyValueServiceImpl implements PropertyValueService {

    @Autowired
    PropertyValueMapper propertyValueMapper;

    @Autowired
    PropertyService propertyService;


    @Override
    public void init(Product product) {
        List<Property> properties = propertyService.list(product.getCid());
        for(Property property : properties) {
            PropertyValue propertyValue = get(property.getId(),product.getId());
            if (null == propertyValue){
                propertyValue = new PropertyValue();
                propertyValue.setPid(product.getId());
                propertyValue.setPtid(property.getId());
                propertyValueMapper.insert(propertyValue);
            }
        }

    }

    @Override
    public void update(PropertyValue propertyValue) {
        propertyValueMapper.updateByPrimaryKeySelective(propertyValue);
        //只会更新传过来的propertyValue对象的值   即propertyvalue只有value值 更新时其他字段不变
    }

    @Override //ptid 属性    pid 产品
    public PropertyValue get(int ptid, int pid) {
        PropertyValueExample propertyValueExample = new PropertyValueExample();
        propertyValueExample.createCriteria().andPtidEqualTo(ptid).andPidEqualTo(pid);
        List<PropertyValue> propertyValues = propertyValueMapper.selectByExample(propertyValueExample);
        if(propertyValues.isEmpty()){
            return null;
        }
        return propertyValues.get(0);
    }

    @Override //得到全部属性值
    public List<PropertyValue> list(int pid) {
        PropertyValueExample propertyValueExample = new PropertyValueExample();
        propertyValueExample.createCriteria().andPidEqualTo(pid);
        List<PropertyValue> propertyValues = propertyValueMapper.selectByExample(propertyValueExample);
        for(PropertyValue propertyValue : propertyValues){
            Property property = propertyService.get(propertyValue.getPtid());
            propertyValue.setProperty(property);
        }
        return propertyValues;
    }










}
