package com.lee.jxmall.order.web;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.lee.jxmall.order.config.AlipayTemplate;
import com.lee.jxmall.order.service.OrderService;
import com.lee.jxmall.order.vo.PayVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PayWebController {

    @Autowired
    AlipayTemplate alipayTemplate;

    @Autowired
    OrderService orderService;

    /**
     * 支付宝支付功能
     *  1、将支付页面让浏览器展示
     *  2、支付成功后，要跳到用户订单列表页
     * @param orderSn
     * @return
     * @throws AlipayApiException
     */
    @ResponseBody
    @GetMapping(value = "/aliPayOrder",produces = "text/html")
    public String aliPayOrder(@RequestParam("orderSn") String orderSn) throws AlipayApiException {

        System.out.println("接收到订单信息orderSn："+orderSn);
        //获取当前订单并设置支付订单相关信息
        PayVo payVo = orderService.getOrderPay(orderSn);
        //换回的是一个页面，直接交给浏览器即可
        String pay = alipayTemplate.pay(payVo);
        return pay;
    }

}
