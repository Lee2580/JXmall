package com.lee.jxmall.order.web;

import com.lee.jxmall.order.service.OrderService;
import com.lee.jxmall.order.vo.OrderConfirmVo;
import com.lee.jxmall.order.vo.OrderSubmitVo;
import com.lee.jxmall.order.vo.SubmitOrderResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    /**
     * 提交订单，下单功能
     * @param submitVo
     * @param model
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/submitOrder")
    public String submitOrder(OrderSubmitVo submitVo, Model model,
                              RedirectAttributes redirectAttributes) {

        //下单， 创建订单、验令牌、验价格、锁库存...
        try {
            // 去OrderServiceImpl服务里验证和下单
            SubmitOrderResponseVo responseVo = orderService.submitOrder(submitVo);

            // 下单失败回到订单重新确认订单信息
            if (responseVo.getCode() == 0) {
                // 下单成功去支付响应
                model.addAttribute("submitOrderResp", responseVo);
                // 支付页
                return "pay";
            } else {
                //下单失败回到订单确认页，重新确认订单信息
                String msg = "下单失败";
                switch (responseVo.getCode()) {
                    case 1:
                        msg += "订单信息过期,请刷新在提交";
                        break;
                    case 2:
                        msg += "订单商品价格发送变化,请确认后再次提交";
                        break;
                    case 3:
                        msg += "商品库存不足";
                        break;
                }
                redirectAttributes.addFlashAttribute("msg", msg);
                // 重定向
                return "redirect:http://order.jxmall.com/toTrade";
            }
        } catch (Exception e) {
            if (e instanceof NotStockException) {
                String message = e.getMessage();
                redirectAttributes.addFlashAttribute("msg", message);
            }
            return "redirect:http://order.jxmall.com/toTrade";
        }
    }
}
