package cn.test.tmall.service;

import cn.test.tmall.mapper.OrderItemMapper;
import cn.test.tmall.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    ProductService productService;

    @Autowired
    OrderItemMapper orderItemMapper;

    @Override
    public void add(OrderItem orderItem) {
        orderItemMapper.insert(orderItem);
    }

    @Override
    public void delete(int id) {
        orderItemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(OrderItem orderItem) {
        orderItemMapper.updateByPrimaryKeySelective(orderItem);
    }

    @Override
    public OrderItem get(int id) {
        OrderItem orderItem = orderItemMapper.selectByPrimaryKey(id);
        setProduct(orderItem);
        return orderItem;

    }

    @Override
    public List<OrderItem> list() {
        OrderItemExample orderItemExample = new OrderItemExample();
        orderItemExample.setOrderByClause("id desc");
        List<OrderItem> orderItems = orderItemMapper.selectByExample(orderItemExample);
        return orderItems;
    }

    @Override
    public void fill(List<Order> orders) {
        for (Order order : orders){
            fill(order);
        }

    }

    @Override
    public void fill(Order order) {
        OrderItemExample orderItemExample = new OrderItemExample();
        orderItemExample.createCriteria().andOidEqualTo(order.getId());
        List<OrderItem> orderItems = orderItemMapper.selectByExample(orderItemExample);

        //为所有的订单项设置Product属性
        setProduct(orderItems);

        //计算该订单的金额与数量
        float total = 0;
        int totalNumber = 0;
        for (OrderItem orderItem : orderItems){
            total +=  orderItem.getProduct().getPromotePrice() * orderItem.getNumber();
            totalNumber += orderItem.getNumber();
        }
        order.setTotal(total);
        order.setTotalNumber(totalNumber);
        order.setOrderItemList(orderItems);
    }

    public void setProduct(List<OrderItem> orderItems){
        for (OrderItem orderItem : orderItems){
            setProduct(orderItem);
        }
    }

    public void setProduct(OrderItem orderItem){
        Product product = productService.get(orderItem.getPid());
        orderItem.setProduct(product);
    }

    @Override
    public int getSaleCount(int pid) {
        OrderItemExample orderItemExample = new OrderItemExample();
        orderItemExample.createCriteria().andPidEqualTo(pid);
        orderItemExample.setOrderByClause("id desc");
        List<OrderItem> orderItems = orderItemMapper.selectByExample(orderItemExample);
        int result = 0;
        for (OrderItem orderItem : orderItems){
            result += orderItem.getNumber();
        }
        return result;
    }


    @Override
    public List<OrderItem> listByUser(int uid) {
        OrderItemExample orderItemExample = new OrderItemExample();
        orderItemExample.createCriteria().andUidEqualTo(uid).andOidIsNull();
        List<OrderItem> orderItems = orderItemMapper.selectByExample(orderItemExample);
        setProduct(orderItems);
        return orderItems;
    }
}
