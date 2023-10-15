package com.service.onestopapp.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.onestopapp.dao.RestaurantOrderDAO;
import com.service.onestopapp.dto.OrderResponseDTO;

@Service
public class RestaurantService {
    @Autowired
    private RestaurantOrderDAO orderDAO;

    public String greeting() {
        return "Welcome to Swiggy Restaurant service";
    }

    public OrderResponseDTO getOrder(String orderId) {
        return orderDAO.getOrders(orderId);
    }
}
