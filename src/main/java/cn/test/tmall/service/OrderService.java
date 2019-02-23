package cn.test.tmall.service;

import cn.test.tmall.pojo.Order;
import cn.test.tmall.pojo.OrderItem;

import java.util.List;

public interface OrderService {

    //订单状态信息
    String waitPay = "waitPay";   //待支付
    String waitDelivery = "waitDelivery"; //待发货
    String waitConfirm = "waitConfirm"; //待确认
    String waitReview = "waitReview"; //待评论
    String finish = "finish"; //结束
    String delete_ = "delete_"; //结束

    void add(Order order);
    void delete(int id);
    void update(Order order);
    Order get(int id);

    List<Order> list();

    float add(Order order, List<OrderItem> orderItems);

    List<Order> list(int uid,String excludedStatus);
}
