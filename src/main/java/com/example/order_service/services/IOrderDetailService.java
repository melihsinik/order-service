package com.example.order_service.services;

import com.example.order_service.models.OrderDetail;

import java.util.List;

public interface IOrderDetailService {
    OrderDetail create(OrderDetail orderDetail);
    void delete(int id);
    OrderDetail get(int id);
    List<OrderDetail> getAll();
}
