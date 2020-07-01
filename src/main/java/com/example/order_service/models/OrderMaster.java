package com.example.order_service.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity(name = "order_master")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class OrderMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int customerID;
    @CreatedDate
    private long createdDate = System.currentTimeMillis();
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetailList = new ArrayList<>();
    //private Map<Integer,OrderDetail> orderDetailMap = new HashMap<>();
}
