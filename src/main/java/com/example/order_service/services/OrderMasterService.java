package com.example.order_service.services;

import com.example.order_service.models.OrderMaster;
import com.example.order_service.repositories.OrderMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class OrderMasterService implements IOrderMasterService {
    @Autowired
    private OrderMasterRepository repository;

    public OrderMaster create(OrderMaster orderMaster){
        return repository.save(orderMaster);
    }

    public void delete(int id){
        if (!repository.existsById(id))
            throw new EntityNotFoundException();

        repository.deleteById(id);
    }

    public OrderMaster update(OrderMaster orderMaster){

        if (!repository.existsById(orderMaster.getId()))
            throw new EntityNotFoundException();

        return repository.save(orderMaster);
    }

    public OrderMaster get(int id){
        if (!repository.existsById(id))
            throw new EntityNotFoundException();
        return repository.getOne(id);
    }

    public List<OrderMaster> getAll(){
        if (repository.count() == 0)
            throw new EntityNotFoundException();
        return repository.findAll();
    }

    public List<OrderMaster> getOrderMastersByCustomerID(int id){
        return repository.getOrderMastersByCustomerID(id);
    }
}
