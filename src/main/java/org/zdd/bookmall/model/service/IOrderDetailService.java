package org.zdd.bookmall.model.service;

import org.zdd.bookmall.model.entity.BookInfo;

import java.util.List;

public interface IOrderDetailService {

    List<BookInfo> findBooksByOrderId(String orderId);
}
