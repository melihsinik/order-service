package com.example.order_service.repositories;

import com.example.order_service.models.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMasterRepository extends JpaRepository<OrderMaster,Integer> {
    List<OrderMaster> getOrderMastersByCustomerID(int id);
}
