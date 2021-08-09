package com.lee.jxmall.order.web;

import com.lee.jxmall.order.service.OrderService;
import com.lee.jxmall.order.vo.OrderConfirmVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.concurrent.ExecutionException;

@Controller
public class OrderWebController {

    @Autowired
    OrderService orderService;

    @GetMapping("/toTrade")
    public String toTrade(Model model) throws ExecutionException, InterruptedException {

        OrderConfirmVo confirmOrder = orderService.confirmOrder();
        //订单确认数据
        model.addAttribute("orderConfirmData",confirmOrder);
        return "confirm";
    }
}
