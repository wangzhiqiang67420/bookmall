package org.zdd.bookmall.model.service;

import org.zdd.bookmall.common.pojo.BSResult;
import org.zdd.bookmall.model.entity.custom.Cart;
import org.zdd.bookmall.model.entity.BookInfo;

import javax.servlet.http.HttpServletRequest;

public interface ICartService {

    BSResult addToCart(BookInfo bookInfo, Cart cart,int buyNum);

    BSResult clearCart(HttpServletRequest request,String sessionName);

    Cart deleteCartItem(Long bookId, HttpServletRequest request);

    Cart updateBuyNum(Long bookId, int newNum, HttpServletRequest request);

    BSResult checkedOrNot(Cart cart,Long bookId);

    BSResult orderCart(Cart cart,Long[] bookIds);

}
