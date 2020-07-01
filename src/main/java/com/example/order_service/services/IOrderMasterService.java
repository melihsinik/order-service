package com.example.order_service.services;

import com.example.order_service.models.OrderMaster;

import java.util.List;

public interface IOrderMasterService {
    OrderMaster create(OrderMaster orderMaster);
    void delete(int id);
    OrderMaster update(OrderMaster orderMaster);
    OrderMaster get(int id);
    List<OrderMaster> getAll();
    List<OrderMaster> getOrderMastersByCustomerID(int id);
}
