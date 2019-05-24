package org.zdd.bookmall.web.controller;

import org.zdd.bookmall.model.entity.custom.Cart;
import org.zdd.bookmall.model.service.ICartService;
import org.zdd.bookmall.common.pojo.BSResult;
import org.zdd.bookmall.model.entity.BookInfo;
import org.zdd.bookmall.model.service.IBookInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/cart")
@CrossOrigin(allowCredentials="true")
public class CartController {

    @Autowired
    private IBookInfoService bookInfoService;

    @Autowired
    private ICartService cartService;

    //返回购物差页面
    @GetMapping("/items")
    public String showCart() {
        return "cart";
    }

    /**
     * 加入购物车
     *
     * @param bookId
     * @param request
     * @return
     */
    @RequestMapping("/addition")
    @ResponseBody
    public String addToCart(@RequestParam(value = "bookId",defaultValue = "0") Long bookId,
                            @RequestParam(required = false,defaultValue = "0") int buyNum,
                            HttpServletRequest request) {

        Cart cart = (Cart) request.getSession().getAttribute("cart");
        //根据要加入购物车的bookId查询bookInfo
        BookInfo bookInfo = bookInfoService.queryBookAvailable(bookId);

        if (bookInfo != null) {
            //这本书在数据库里
            BSResult bsResult = cartService.addToCart(bookInfo, cart, buyNum);
            request.getSession().setAttribute("cart", bsResult.getData());
            request.setAttribute("bookInfo", bookInfo);
        } else {
            //数据库里没有这本书,或库存不足
            request.setAttribute("bookInfo", null);
            return "fail";
        }
        return "addcart";
    }

    @GetMapping("/clear")
    @ResponseBody
    public String clearCart(HttpServletRequest request) {
        cartService.clearCart(request,"cart");
        return "cart";
    }

    @GetMapping("/deletion/{bookId}")
    @ResponseBody
    public Cart deleteCartItem(@PathVariable("bookId") Long bookId,HttpServletRequest request){
        return cartService.deleteCartItem(bookId, request);
    }

    /**
     * 更新某个购物车项的购买数量
     * @param request
     * @return
     */
    @PostMapping("/buy/num/update")
    @ResponseBody
    public Cart updateBuyNum(@RequestBody Map<String,String> map, HttpServletRequest request){
        Long bookId = Long.parseLong(map.get("bookId").toString());
        int newNum = Integer.parseInt(map.get("newNum").toString());
        return cartService.updateBuyNum(bookId, newNum, request);
    }

    @PostMapping("/checkOne")
    @ResponseBody
    public BSResult checkACartItem(Long bookId,HttpServletRequest request){
        Cart cart = (Cart)request.getSession().getAttribute("cart");
        return cartService.checkedOrNot(cart, bookId);
    }

    @PostMapping("/checkBook")
    @ResponseBody
    public BSResult checkBook(@RequestBody Map<String,String> map,HttpServletRequest request){
        Long bookId = Long.parseLong(map.get("bookId").toString());
        Cart cart = (Cart)request.getSession().getAttribute("cart");
        return cartService.checkedOrNot(cart, bookId);
    }

    @GetMapping("/getCart")
    @ResponseBody
    public Cart getCart(HttpServletRequest request){
        Cart cart = (Cart)request.getSession().getAttribute("cart");
        return cart;
    }
}
