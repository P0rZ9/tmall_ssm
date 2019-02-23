package cn.test.tmall.service;

import cn.test.tmall.mapper.OrderMapper;
import cn.test.tmall.pojo.Order;
import cn.test.tmall.pojo.OrderExample;
import cn.test.tmall.pojo.OrderItem;
import cn.test.tmall.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    UserService userService;

    @Autowired
    OrderItemService orderItemService;

    @Override
    public void add(Order order) {
        orderMapper.insert(order);
    }


    @Override
    public void delete(int id) {
        orderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Order order) {
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public Order get(int id) {
        Order order = orderMapper.selectByPrimaryKey(id);
        return order;
    }

    @Override
    public List<Order> list() {
        OrderExample orderExample = new OrderExample();
        orderExample.setOrderByClause("id desc");
        List<Order> orders = orderMapper.selectByExample(orderExample);
        setUser(orders);
        return orders;
    }


    public void setUser(List<Order> orders){
        for(Order order : orders){
            setUser(order);
        }
    }

    public void setUser(Order order){
        int uid = order.getUid();
        User user = userService.get(uid);
        order.setUser(user);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackForClassName="Exception")
    public float add(Order order, List<OrderItem> orderItems) {
       float total = 0;
        add(order);
       if(false)
           throw new RuntimeException();
        for(OrderItem orderItem : orderItems){
            orderItem.setOid(order.getId());
            orderItemService.update(orderItem);
            total += orderItem.getProduct().getPromotePrice()*orderItem.getNumber();
        }
        return total;


    }

    @Override
    public List<Order> list(int uid, String excludedStatus) {
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria().andUidEqualTo(uid).andStatusNotEqualTo(excludedStatus);
        orderExample.setOrderByClause("id desc");
        List<Order> orders = orderMapper.selectByExample(orderExample);
        return orders;
    }
}
