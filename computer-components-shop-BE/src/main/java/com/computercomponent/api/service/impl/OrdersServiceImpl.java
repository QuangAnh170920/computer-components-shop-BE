package com.computercomponent.api.service.impl;

import com.computercomponent.api.common.Const;
import com.computercomponent.api.common.OrderStatus;
import com.computercomponent.api.dto.OrderDetailDTO;
import com.computercomponent.api.dto.OrdersDTO;
import com.computercomponent.api.dto.OrdersManagementDTO;
import com.computercomponent.api.dto.OrdersUpdateRequestDTO;
import com.computercomponent.api.entity.OrderDetail;
import com.computercomponent.api.entity.Orders;
import com.computercomponent.api.entity.User;
import com.computercomponent.api.repository.OrderDetailRepository;
import com.computercomponent.api.repository.OrdersRepository;
import com.computercomponent.api.repository.UserRepository;
import com.computercomponent.api.request.OrdersRequest;
import com.computercomponent.api.response.OrderDetailResponse;
import com.computercomponent.api.service.OrdersService;
import com.computercomponent.api.until.DataUtil;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private UserRepository userRepository;

    //chưa validate
    @Override
    public String createOrder(OrdersDTO ordersDTO) {
        Orders orders = new Orders();
        BeanUtils.copyProperties(ordersDTO, orders);
        ordersRepository.save(orders);
        for (OrderDetailDTO detailDTO : ordersDTO.getOrderDetail()) {
            OrderDetail detail = new OrderDetail();
            detail.setOrderId(orders.getId());
            detail.setProductId(detailDTO.getProductId());
            detail.setQuantity(detailDTO.getQuantity());
            orderDetailRepository.save(detail);
        }
        return Const.MESSAGE_CODE.SUCCESS;
    }

    @Override
    public Page<OrdersManagementDTO> getList(OrdersRequest ordersRequest) {
        PageRequest pageRequest = DataUtil.getPageable(ordersRequest.getPageNumber(), ordersRequest.getPageSize());
        if (ordersRequest.getSearchField() == null) {
            ordersRequest.setSearchField("");
        }
        if (ordersRequest.getStatus() != null) {
            OrderStatus status = OrderStatus.fromValue(ordersRequest.getStatus());
            Assert.notNull(status, Const.MESSAGE_CODE.STATUS_NOT_FOUND);
        }
        if (ordersRequest.getUserId() != null) {
            User user = userRepository.findUserById(ordersRequest.getUserId());
            Assert.notNull(user, Const.USER.USER_NOT_FOUND);
        }
        return ordersRepository.findAllAndSearch(ordersRequest.getSearchField().trim(), ordersRequest.getStatus(), ordersRequest.getUserId(), pageRequest);
    }

    // cần validate
    @Override
    public OrdersUpdateRequestDTO update(OrdersUpdateRequestDTO ordersUpdateRequestDTO) {
        Orders orders = ordersRepository.findOrdersById(ordersUpdateRequestDTO.getId());
        Assert.isTrue(orders != null, Const.ORDERS.ORDERS_NOT_FOUND);
        BeanUtils.copyProperties(ordersUpdateRequestDTO, orders);
        ordersRepository.save(orders);
        for (OrderDetailDTO detailDTO : ordersUpdateRequestDTO.getOrderDetails()) {
            OrderDetail detail = new OrderDetail();
            detail.setOrderId(orders.getId());
            detail.setProductId(detailDTO.getProductId());
            detail.setQuantity(detailDTO.getQuantity());
            orderDetailRepository.save(detail);
        }
        return null;
    }

    @Override
    public String delete(Long id) {
        Orders orders = ordersRepository.findOrdersById(id);
        Assert.isTrue(orders != null, Const.ORDERS.ORDERS_NOT_FOUND);
        orders.setDeleted(true);
        ordersRepository.save(orders);
        return null;
    }

    @Override
    public com.computercomponent.api.response.OrderDetail getDetail(Long id) {
        com.computercomponent.api.response.OrderDetail orderDetail = ordersRepository.getDetail(id);
        if (orderDetail != null) {
            List<OrderDetailResponse> orderDetailResponses = orderDetailRepository.getDetail(id);
            orderDetail.setDetails(orderDetailResponses);
        }
        return orderDetail;
    }
}
