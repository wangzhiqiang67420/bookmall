package org.zdd.bookmall.model.service;

import org.zdd.bookmall.common.pojo.BSResult;
import org.zdd.bookmall.model.entity.custom.Cart;
import org.zdd.bookmall.model.entity.BookInfo;

import javax.servlet.http.HttpServletRequest;

public interface ICartService {

    BSResult addToCart(BookInfo bookInfo, Cart cart,int buyNum);

    BSResult clearCart(HttpServletRequest request,String sessionName);

    BSResult deleteCartItem(int bookId, HttpServletRequest request);

    BSResult updateBuyNum(int bookId, int newNum, HttpServletRequest request);

    BSResult checkedOrNot(Cart cart,int bookId);

}
