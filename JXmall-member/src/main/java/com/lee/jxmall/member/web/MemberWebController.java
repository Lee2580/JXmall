package com.lee.jxmall.member.web;

import com.lee.common.utils.PageUtils;
import com.lee.common.utils.R;
import com.lee.jxmall.member.feign.OrderFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MemberWebController {

    @Autowired
    OrderFeignService orderFeignService;

    @RequestMapping("/memberOrder.html")
    public String memberOrder(
            @RequestParam(value = "pageNum",required = false,defaultValue = "0") Integer pageNum,
            Model model){

        Map<String, Object> params = new HashMap<>();
        params.put("page", pageNum.toString());
        //远程查询订单服务订单数据
        R r = orderFeignService.listWithItem(params);
        model.addAttribute("orders", r);
        //返回至订单详情页
        return "orderList";
    }

}
