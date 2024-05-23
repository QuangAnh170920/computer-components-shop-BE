package com.computercomponent.api.service;

import com.computercomponent.api.dto.OrdersDTO;
import com.computercomponent.api.dto.OrdersManagementDTO;
import com.computercomponent.api.dto.OrdersUpdateRequestDTO;
import com.computercomponent.api.request.OrdersRequest;
import com.computercomponent.api.response.OrderDetail;
import org.springframework.data.domain.Page;

public interface OrdersService {
    String createOrder(OrdersDTO ordersDTO);

    Page<OrdersManagementDTO> getList(OrdersRequest ordersRequest);

    OrdersUpdateRequestDTO update(OrdersUpdateRequestDTO ordersUpdateRequestDTO);

    String delete(Long id);

    OrderDetail getDetail(Long id);

}
