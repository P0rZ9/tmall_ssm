package cn.test.tmall.controller;


import cn.test.tmall.pojo.*;
import cn.test.tmall.service.*;
import com.github.pagehelper.PageHelper;
import comparator.*;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("")
public class ForeController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Autowired
    ProductImageService productImageService;

    @Autowired
    PropertyValueService propertyValueService;

    @Autowired
    ReviewService reviewService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemService orderItemService;

    //首页
    @RequestMapping(value = "/fore_home", method = RequestMethod.GET)
    public String home(ModelMap modelMap){
        List<Category> categories = categoryService.list();
        productService.fill(categories);
        productService.fillByRow(categories);
        modelMap.addAttribute("cs",categories);
        return "fore/home";
    }

    //注册
    @RequestMapping(value = "/fore_register",method = RequestMethod.POST)
    public String register(ModelMap modelmap, User user){
        String name = user.getName();
        name = HtmlUtils.htmlEscape(name);
        user.setName(name);
        boolean exist = userService.isExist(name);

        if(exist){
            modelmap.addAttribute("message","已存在该用户");
            modelmap.addAttribute("user",null);
            return "fore/register";
        }
        userService.add(user);
        return "redirect:register_success";
    }

    //登陆处理
    @RequestMapping(value = "fore_login",method = RequestMethod.POST)
    public String login(ModelMap modelMap, User user, HttpSession session){
        String name = HtmlUtils.htmlEscape(user.getName());
        String password = HtmlUtils.htmlEscape(user.getPassword());
        User user1 = userService.get(name,password);

        if(user1 == null){
            modelMap.addAttribute("msg","账号密码错误");
            return "fore/login";
        }
        session.setAttribute("user",user1);
        return "redirect:fore_home";

    }

    //退出
    @RequestMapping(value = "fore_logout",method = RequestMethod.GET)
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:fore_home";
    }

    //产品
    @RequestMapping(value = "fore_product",method = RequestMethod.GET)
    public String product(int pid,ModelMap modelMap){
        Product product = productService.get(pid);

        //单个与详情图片
        List<ProductImage> productSingleImages = productImageService.list(product.getId(),ProductImageService.type_single);
        List<ProductImage> productDetailImages = productImageService.list(product.getId(),ProductImageService.type_detail);
        product.setProductDetailImages(productDetailImages);
        product.setProductSingleImages(productSingleImages);
        product.setFirstProductImage(productSingleImages.get(0));

        List<Review> reviews = reviewService.list(product.getId());

        List<PropertyValue> propertyValues = propertyValueService.list(product.getId());
        productService.setSaleAndReviewCount(product);

        modelMap.addAttribute("p",product);
        modelMap.addAttribute("pvs",propertyValues);
        modelMap.addAttribute("reviews",reviews);

        return "fore/product";
    }


    //检查登陆
    @RequestMapping(value = "fore_check_login")
    @ResponseBody
    public String checklogin(HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user != null)
            return "true";
        return "fail";
    }


    @RequestMapping(value = "fore_login_ajax")
    @ResponseBody
    public String loginAjax(User user,HttpSession session){
        String name = HtmlUtils.htmlEscape(user.getName());
        String password = HtmlUtils.htmlEscape(user.getPassword());
        User user1 = userService.get(name,password);
        if(user1 != null) {
            session.setAttribute("user", user1);
            return "true";
        }
        return "fail";
    }

    //购买产品
    @RequestMapping(value = "fore_buy_one")
    public String buy(int pid,int num,ModelMap modelMap,HttpSession session){
        Product product = productService.get(pid);
        boolean oi_found = false;
        int oiid = 0;

        User user = (User) session.getAttribute("user");
        List<OrderItem> orderItems = orderItemService.listByUser(user.getId());

        for(OrderItem orderItem : orderItems){
            if(orderItem.getPid() == pid){
                orderItem.setNumber(orderItem.getNumber()+num);
                orderItemService.update(orderItem);
                oiid = orderItem.getId();
                oi_found = true;
                break;
            }
        }
        if(!oi_found){
            OrderItem orderItem1 = new OrderItem();
            orderItem1.setNumber(num);
            orderItem1.setUid(user.getId());
            orderItem1.setPid(pid);
            orderItemService.add(orderItem1);
            oiid = orderItem1.getId();
        }
        return "redirect:fore_buy?oiid="+oiid;
    }

    //结算页面  处理购物车与直接购买过来的请求
    @RequestMapping(value = "fore_buy")
    public String buy(String[] oiid,ModelMap modelMap,HttpSession session){
        //显示订单页
        List<OrderItem> orderItems = new ArrayList<>();
        float total = 0;
        for(String oiid_ : oiid){
            int oiid1 = Integer.parseInt(oiid_);
            OrderItem orderItem = orderItemService.get(oiid1);
            total += orderItem.getProduct().getPromotePrice() * orderItem.getNumber();
            orderItems.add(orderItem);
        }
        //为后面创建订单
        session.setAttribute("ois",orderItems);
        modelMap.addAttribute("total",total);

        return "fore/buy";
    }


    //增加购物车
    @RequestMapping(value = "fore_add_cart")
    @ResponseBody
    public String addCart(int pid,int num,ModelMap modelMap,HttpSession session){
        Product product = productService.get(pid);
        boolean oi_found = false;
        int oiid = 0;

        User user = (User) session.getAttribute("user");
        List<OrderItem> orderItems = orderItemService.listByUser(user.getId());

        for(OrderItem orderItem : orderItems){
            if(orderItem.getPid() == pid){
                orderItem.setNumber(orderItem.getNumber()+num);
                orderItemService.update(orderItem);
                oi_found = true;
                break;
            }
        }
        if(!oi_found){
            OrderItem orderItem1 = new OrderItem();
            orderItem1.setNumber(num);
            orderItem1.setUid(user.getId());
            orderItem1.setPid(pid);
            orderItemService.add(orderItem1);
        }
        return "true";
    }

    @RequestMapping(value = "fore_category")
    public String category(int cid,String sort,ModelMap modelMap){
        Category category = categoryService.get(cid);
        productService.fill(category);
        productService.setSaleAndReviewCount(category.getProducts());

        if(sort != null) {
            switch (sort){
                case "review":
                    Collections.sort(category.getProducts(), new ProductReviewComparator());
                    break;
                case "date":
                    Collections.sort(category.getProducts(),new ProductDateComparator());
                    break;
                case "saleCount":
                    Collections.sort(category.getProducts(),new ProductSaleCountComparator());
                    break;
                case "price":
                    Collections.sort(category.getProducts(),new ProductPriceComparator());
                    break;
                case "all":
                    Collections.sort(category.getProducts(),new ProductAllComparator());
                    break;
            }
        }

        modelMap.addAttribute("c",category);
        return "fore/category";
    }

    @RequestMapping(value = "fore_search")
    public String search(String keyword,ModelMap modelMap){
        PageHelper.offsetPage(0,20);
        List<Product> products = productService.search(keyword);
        productService.setSaleAndReviewCount(products);
        modelMap.addAttribute("ps",products);
        return "fore/search_result";

    }

    //查看购物车
    @RequestMapping(value = "fore_cart")
    public String cart(ModelMap modelMap,HttpSession session){
        User user = (User) session.getAttribute("user");
        List<OrderItem> orderItems = orderItemService.listByUser(user.getId());
        modelMap.addAttribute("ois",orderItems);
        return "fore/cart";
    }

    //更改购物车product的数量
    @RequestMapping(value = "fore_change_order_item")
    @ResponseBody
    public String changeOrderItem(int number,int pid,HttpSession session){
        Product product = productService.get(pid);
        User user = (User) session.getAttribute("user");
        if(user == null)
            return "fail";
        List<OrderItem> orderItems = orderItemService.listByUser(user.getId());

        for(OrderItem orderItem : orderItems){
            if(orderItem.getPid() == pid) {
                orderItem.setNumber(number);
                orderItemService.update(orderItem);
                break;
            }
        }
        return "true";
    }

    //购物车中 删除
    @RequestMapping(value = "fore_delete_order_item")
    @ResponseBody
    public String deleteOrderItem(int oiid,HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null)
            return "fail";
        orderItemService.delete(oiid);
        return "true";
    }

    //创建订单
    @RequestMapping(value = "fore_create_order")
    public String createOrder(Order order,HttpSession session,ModelMap modelMap){
        User user = (User) session.getAttribute("user");
        List<OrderItem> orderItems = (List<OrderItem>) session.getAttribute("ois");
        String order_code = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + RandomUtils.nextInt(10000);
        order.setCreateDate(new Date());
        order.setOrderCode(order_code);
        order.setUid(user.getId());
        order.setStatus(OrderService.waitPay);
        float total = orderService.add(order,orderItems);

        return "redirect:fore_alipay?oid="+order.getId()+"&total="+total;
    }

    //支付
    @RequestMapping("fore_payed")
    public String payed(int oid,float total,ModelMap modelMap,HttpSession session){
        Order order = orderService.get(oid);
        order.setPayDate(new Date());
        order.setStatus(OrderService.waitDelivery);
        orderService.update(order);
        modelMap.addAttribute("o",order);
        return "fore/payed";
    }


    //已购产品
    @RequestMapping(value = "fore_bought")
    public String bought(HttpSession session,ModelMap modelMap){
        User user = (User) session.getAttribute("user");
        List<Order> orders = orderService.list(user.getId(),OrderService.delete_);
        orderItemService.fill(orders);
        modelMap.addAttribute("os",orders);

        return "fore/bought";
    }

    //确认付款
    @RequestMapping(value = "fore_confirm_pay")
    public String confirm(ModelMap modelMap,int oid){
        Order order = orderService.get(oid);
        orderItemService.fill(order);
        modelMap.addAttribute("o",order);
        return "fore/confirm_pay";
    }

    //确认收货
    @RequestMapping(value = "fore_order_confirmed")
    public String orderconfirmed(ModelMap modelMap,int oid){
        Order order = orderService.get(oid);
        order.setStatus(OrderService.waitReview);
        order.setConfirmDate(new Date());
        orderService.update(order);

        return "fore/order_confirmed";
    }

    //删除订单
    @RequestMapping(value = "fore_delete_order")
    public String delete_order(int oid,ModelMap modelMap){
        Order order = orderService.get(oid);
        order.setStatus(OrderService.delete_);
        orderService.update(order);
        return "true";
    }

    @RequestMapping(value = "fore_review")
    public String review(ModelMap modelMap,int oid){
        Order order = orderService.get(oid);
        orderItemService.fill(order);
        Product product = order.getOrderItemList().get(0).getProduct();
        List<Review> reviews = reviewService.list(product.getId());
        productService.setSaleAndReviewCount(product);
        modelMap.addAttribute("p",product);
        modelMap.addAttribute("o",order);
        modelMap.addAttribute("reviews",reviews);
        return "fore/review";
    }

    @RequestMapping(value = "fore_do_review")
    public String doreview(ModelMap modelMap, HttpSession session, @RequestParam("oid") int oid,@RequestParam("pid") int pid,String content){
        Order order = orderService.get(oid);
        order.setStatus(OrderService.finish);
        orderService.update(order);

        Product product = productService.get(pid);
        content = HtmlUtils.htmlEscape(content);

        User user = (User) session.getAttribute("user");

        Review review = new Review();

        review.setContent(content);
        review.setCreateDate(new Date());
        review.setPid(product.getId());
        review.setUid(user.getId());
        reviewService.add(review);

        return "redirect:fore_review?oid=" + oid + "&showonly=true";
    }


























}
