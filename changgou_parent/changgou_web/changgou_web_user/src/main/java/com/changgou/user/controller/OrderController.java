package com.changgou.user.controller;

import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.pojo.Sku;
import com.changgou.order.feign.OrderFeign;
import com.changgou.order.feign.OrderItemFeign;
import com.changgou.order.pojo.Order;
import com.changgou.order.pojo.OrderItem;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.TokenDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author zl
 * @version V1.0
 * @Description: TODO
 * @date 2019/9/26 20:32
 **/
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Controller
@RequestMapping("orderweb")
public class OrderController {
    @Autowired
    private OrderFeign orderFeign;
    @Autowired
    private OrderItemFeign orderItemFeign;
    @Autowired
    private SkuFeign skuFeign;
    @RequestMapping("userOrder")
    public String showOrderByName(@RequestParam(required = false) Integer pageNum, Model model){
        Integer size=5;
        if (pageNum == null) {
            pageNum=1;
        }
        PageInfo pageInfo = orderFeign.findPage2(pageNum, size);
        List<Order> orders = pageInfo.getList();
        if (orders != null && orders.size()>0) {
            //遍历order拿到订单信息
            for (Order order : orders) {
                //获取订单对应的所有订单详情
                OrderItem orderItemwhere=new OrderItem();
                orderItemwhere.setOrderId(order.getId());
                List<OrderItem> orderItems = orderItemFeign.findList(orderItemwhere).getData();
                order.setOrderItems(orderItems);
                if (orderItems!=null && orderItems.size()>0){
                    //获取页面显示的规格数据
                    for (OrderItem orderItem : orderItems) {
                        Sku sku = skuFeign.findById(orderItem.getSkuId()).getData();
                        orderItem.setSpec(sku.getSpec());
                    }
                }
            }
        }
        model.addAttribute("orders",orders);
        return "home-index";
    }
}
