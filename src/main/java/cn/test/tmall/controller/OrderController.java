package cn.test.tmall.controller;


import cn.test.tmall.pojo.Order;
import cn.test.tmall.service.OrderItemService;
import cn.test.tmall.service.OrderService;
import cn.test.tmall.util.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemService orderItemService;

    @RequestMapping(value = "/admin_order_list", method = RequestMethod.GET)
    public String list(ModelMap modelMap, Page page){
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Order> orderList = orderService.list();
        int total = (int) new PageInfo<>(orderList).getTotal();
        page.setTotal(total);
        orderItemService.fill(orderList);

        modelMap.addAttribute("orderList",orderList);
        modelMap.addAttribute("page",page);
        return "admin/list_order";
    }

    @RequestMapping(value = "/admin_order_delivery", method = RequestMethod.GET)
    public String delivery(ModelMap modelMap,Order order){
        order.setDeliveryDate(new Date());
        order.setStatus(OrderService.waitConfirm);
        orderService.update(order);


        return "redirect:/admin_order_list";
    }


}
