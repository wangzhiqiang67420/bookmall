package org.zdd.bookmall.model.entity.custom;

import java.util.HashMap;
import java.util.Map;

/**
 * 购物车实体类
 */
public class Cart {

    private Map<Long,CartItem> cartItems = new HashMap<>();

    private double total;

    public Map<Long, CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Map<Long, CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
