package com.example.order_service.controllers;

import com.example.order_service.models.OrderDetail;
import com.example.order_service.services.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Controller
@RequestMapping("/orderDetail")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping("**/save")
    public ResponseEntity save(@RequestBody OrderDetail orderDetail){
        OrderDetail createdOrderDetail;
        try {
            createdOrderDetail = orderDetailService.create(orderDetail);
        }
        catch (Exception e){
            return new ResponseEntity("Not created order detail", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(createdOrderDetail,HttpStatus.CREATED);
    }
    @DeleteMapping("**/{id}")
    public ResponseEntity delete(@PathVariable int id){
        try {
            orderDetailService.delete(id);
        }
        catch (EntityNotFoundException e){
            return new ResponseEntity("Not found order detail with ID: "+id,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity("Deleted order detail with ID: "+id,HttpStatus.OK);
    }
    @GetMapping("**/{id}")
    public ResponseEntity get(@PathVariable int id){
        OrderDetail orderDetail;
        try {
            orderDetail = orderDetailService.get(id);
        }
        catch (EntityNotFoundException e){
            return new ResponseEntity("Not found order detail with ID: "+id,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(orderDetail,HttpStatus.FOUND);
    }
    @GetMapping("**/orderDetails")
    public ResponseEntity getAll(){
        List<OrderDetail> orderDetails;
        try {
            orderDetails = orderDetailService.getAll();
        }
        catch (EntityNotFoundException e){
            return new ResponseEntity("Databse is Empty",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(orderDetails,HttpStatus.FOUND);
    }
}
