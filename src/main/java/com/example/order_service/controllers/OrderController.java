package com.example.order_service.controllers;

import com.example.order_service.models.OrderDetail;
import com.example.order_service.models.OrderDetailDTO;
import com.example.order_service.models.OrderMaster;
import com.example.order_service.repositories.OrderMasterRepository;
import com.example.order_service.rest.client.CustomerClient;
import com.example.order_service.services.OrderDetailService;
import com.example.order_service.services.OrderMasterService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.websocket.server.PathParam;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderMasterService orderMasterService;
    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private CustomerClient customerClient;

    @GetMapping(value = "**/create",params = "customerID")
    public ResponseEntity create(@PathParam(value = "customerID") int customerID){
        boolean verified;
        OrderMaster orderMaster = new OrderMaster();
        OrderMaster createdOrderMaster;
        try {
            verified = customerClient.isVerified(customerID);
            System.out.println(verified);
        }
        catch (NotFoundException exception){
            return new ResponseEntity(exception.getMessage(),HttpStatus.NOT_FOUND);
        }

        if (verified){
            orderMaster.setCustomerID(customerID);
            createdOrderMaster = orderMasterService.create(orderMaster);

            return new ResponseEntity(createdOrderMaster,HttpStatus.CREATED);
        }
        else
            return new ResponseEntity("Customer is unauthorized",HttpStatus.UNAUTHORIZED);

    }
    @GetMapping(value = "**/add",params = {"customerID","orderMasterID"})
    public ResponseEntity add(@RequestParam(value = "customerID") int customerID, @RequestParam(value = "orderMasterID") int orderMasterID, @RequestBody OrderDetailDTO orderDetailDTO){
        boolean verified;
        OrderMaster orderMaster;
        OrderMaster updatedOrderMaster;
        try {
            verified = customerClient.isVerified(customerID);
        }
        catch (NotFoundException exception){
            return new ResponseEntity(exception.getMessage(),HttpStatus.NOT_FOUND);
        }
        try {
            orderMaster = orderMasterService.get(orderMasterID);
            if (orderMaster.getCustomerID()!=customerID)
                return new ResponseEntity("Customer id does not match",HttpStatus.UNAUTHORIZED);
        }
        catch (EntityNotFoundException e){
            return new ResponseEntity("Not found order master with ID: "+orderMasterID,HttpStatus.NOT_FOUND);
        }


        if (verified){
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setDetail(orderDetailDTO.getDetail());
            orderMaster.getOrderDetailList().add(orderDetail);
            updatedOrderMaster = orderMasterService.update(orderMaster);

            return new ResponseEntity(updatedOrderMaster,HttpStatus.OK);
        }
        else
            return new ResponseEntity("Customer is unauthorized",HttpStatus.UNAUTHORIZED);

    }
    @GetMapping(value = "**/remove",params = {"customerID","orderMasterID","orderDetailIndex"})
    public ResponseEntity remove(@RequestParam(value = "customerID") int customerID,@RequestParam(value = "orderMasterID") int orderMasterID,@RequestParam(value = "orderDetailIndex") int orderDetailIndex){
        boolean verified;
        OrderMaster orderMaster;
        OrderMaster updatedOrderMaster;
        try {
            verified = customerClient.isVerified(customerID);
            System.out.println(verified);
        }
        catch (NotFoundException exception){
            return new ResponseEntity(exception.getMessage(),HttpStatus.NOT_FOUND);
        }
        try {
            orderMaster = orderMasterService.get(orderMasterID);
        }
        catch (EntityNotFoundException e){
            return new ResponseEntity("Not found order master with ID: "+orderMasterID,HttpStatus.NOT_FOUND);
        }

        if (verified){
            orderMaster.getOrderDetailList().remove(orderDetailIndex);
            updatedOrderMaster = orderMasterService.update(orderMaster);

            return new ResponseEntity(updatedOrderMaster,HttpStatus.OK);
        }
        else
            return new ResponseEntity("Customer is unauthorized",HttpStatus.UNAUTHORIZED);

    }

    @DeleteMapping("**/{id}")
    public ResponseEntity delete(@PathVariable int id){
        try {
            orderMasterService.delete(id);
        }
        catch (EntityNotFoundException e){
            return new ResponseEntity("Not found order detail with ID: "+id,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity("Deleted order detail with ID: "+id,HttpStatus.OK);
    }
    @GetMapping("**/{id}")
    public ResponseEntity get(@PathVariable int id){
        OrderMaster orderMaster;
        try {
            orderMaster = orderMasterService.get(id);
        }
        catch (EntityNotFoundException e){
            return new ResponseEntity("Not found order detail with ID: "+id,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(orderMaster,HttpStatus.FOUND);
    }
    @GetMapping(value = "**/",params = "customerID")
    public ResponseEntity getOrderMasterByCustomerID(@RequestParam int customerID){
        List<OrderMaster> orderMasters;
        boolean verified;

        try {
            verified = customerClient.isVerified(customerID);
        }
        catch (NotFoundException exception){
            return new ResponseEntity(exception.getMessage(),HttpStatus.NOT_FOUND);
        }

        if (verified){
            orderMasters = orderMasterService.getOrderMastersByCustomerID(customerID);

            return new ResponseEntity(orderMasters,HttpStatus.FOUND);
        }
        else
            return new ResponseEntity("Customer is unauthorized",HttpStatus.UNAUTHORIZED);
    }
}
