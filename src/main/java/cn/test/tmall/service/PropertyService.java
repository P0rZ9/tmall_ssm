package cn.test.tmall.service;

import cn.test.tmall.pojo.Property;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PropertyService {


    List<Property> list(int cid);


    void add(Property property);
    void delete(int id);
    void update(Property property);
    Property get(int id);

    int total();



}
