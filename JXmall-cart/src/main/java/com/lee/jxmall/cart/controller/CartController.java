package com.lee.jxmall.cart.controller;

import com.lee.common.constant.AuthServerConstant;
import com.lee.common.constant.CartConstant;
import com.lee.jxmall.cart.interceptor.CartInterceptor;
import com.lee.jxmall.cart.service.CartService;
import com.lee.jxmall.cart.vo.CartItemVo;
import com.lee.jxmall.cart.vo.UserInfoTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.concurrent.ExecutionException;

@Controller
public class CartController {

    @Autowired
    CartService cartService;

    /**
     * 跳转购物车页面
     *      浏览器有一个cookie，里面有个user-key：标识用户身份，一个月后过期
     *      第一次使用购物车功能，都会给一个临时用户身份
     *      浏览器以后保存，每次访问都会带上这个cookie
     *  登录了：session有
     *  没登陆：按cookie里的user-key做
     *  第一次，没有临时用户，要创建
     * @return
     */
    @GetMapping("/cart.html")
    public String cartListPage(HttpSession httpSession){

        //1、快速获得用户信息， id：user-key
        UserInfoTo userInfoTo = CartInterceptor.userInfoToThreadLocal.get();

        return "cartList";
    }

    /**
     * 添加商品到购物车
     *      attributes.addFlashAttribute():将数据放在session中，可以在页面中取出，但是只能取一次
     *      attributes.addAttribute():将数据放在url后面
     * @return
     */
    @GetMapping("/addToCart")
    public String addToCart(@RequestParam("skuId") Long skuId,@RequestParam("num") Integer num,
                            RedirectAttributes attributes) throws ExecutionException, InterruptedException {

        CartItemVo cartItemVo= cartService.addToCart(skuId, num);

        attributes.addAttribute("skuId", skuId);
        return "redirect:http://cart.jxmall.com/addToCartSuccessPage.html";
    }

    /**
     * 跳转到添加购物车成功页面
     * @param skuId
     * @param model
     * @return
     */
    @GetMapping("/addToCartSuccessPage.html")
    public String addToCartSuccessPage(@RequestParam("skuId") Long skuId, Model model) {

        //重定向到成功页面。再次查询购物车数据即可
        CartItemVo cartItemVo = cartService.getCartItem(skuId);
        model.addAttribute("cartItem", cartItemVo);
        return "success";
    }
}
