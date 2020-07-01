package com.example.order_service.services;

import com.example.order_service.models.OrderDetail;
import com.example.order_service.models.OrderMaster;
import com.example.order_service.repositories.OrderDetailRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class OrderDetailService implements IOrderDetailService {

    @Autowired
    private OrderDetailRepository repository;

    public OrderDetail create(OrderDetail orderDetail){
        return repository.save(orderDetail);
    }

    public void delete(int id){
        if (!repository.existsById(id))
            throw new EntityNotFoundException();

        repository.deleteById(id);
    }

    public OrderDetail get(int id){
        if (!repository.existsById(id))
            throw new EntityNotFoundException();
        return repository.getOne(id);
    }

    public List<OrderDetail> getAll(){
        if (repository.count() == 0)
            throw new EntityNotFoundException();
        return repository.findAll();
    }
}
