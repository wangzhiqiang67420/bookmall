package org.zdd.bookmall.model.service.impl;

import org.zdd.bookmall.model.dao.BookInfoMapper;
import org.zdd.bookmall.model.dao.OrderDetailMapper;
import org.zdd.bookmall.model.entity.BookInfo;
import org.zdd.bookmall.model.entity.OrderDetail;
import org.zdd.bookmall.model.service.IOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailServiceImpl implements IOrderDetailService {

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private BookInfoMapper bookInfoMapper;

    @Override
    public List<BookInfo> findBooksByOrderId(String orderId) {

        Example example = new Example(OrderDetail.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId", orderId);
        List<OrderDetail> orderDetails = orderDetailMapper.selectByExample(example);

        List<BookInfo> bookInfos = orderDetails.stream()
                .map(orderDetail -> bookInfoMapper.selectByPrimaryKey(orderDetail.getBookId()))
                .collect(Collectors.toList());

        return bookInfos;
    }

}
