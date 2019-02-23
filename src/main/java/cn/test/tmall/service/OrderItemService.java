package cn.test.tmall.service;

import cn.test.tmall.pojo.Order;
import cn.test.tmall.pojo.OrderItem;

import java.util.List;

public interface OrderItemService {


    void add(OrderItem orderItem);
    void delete(int id);
    void update(OrderItem orderItem);
    OrderItem get(int id);

    List<OrderItem> list();

    void fill(List<Order> orders);
    void fill(Order order);

    //根据产品获取销售量
    int getSaleCount(int pid);

    //根据user列出OrderItem
    List<OrderItem> listByUser(int uid);
}
