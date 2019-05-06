package org.zdd.bookmall.model.service;

import org.zdd.bookmall.model.entity.custom.Cart;
import org.zdd.bookmall.model.entity.custom.OrderCustom;
import org.zdd.bookmall.pay.PayContext;
import org.zdd.bookmall.common.pojo.BSResult;
import org.zdd.bookmall.model.entity.User;

import java.util.List;

public interface IOrderService {

    List<OrderCustom> findOrdersByUserId(int userId);

    List<OrderCustom> findOrdersByStoreId(int storeId);

    BSResult createOrder(Cart cart, User user, String express, int payMethod);

    BSResult findOrderById(String orderId);

    BSResult deleteOrder(String orderId);

    void updateOrderAfterPay(PayContext payContext);

    BSResult postOrder(String orderId);

    OrderCustom findOrderCustomById(String orderId);

    BSResult updateOrder(OrderCustom orderCustom);

    BSResult confirmReceiving(String orderId);
}
