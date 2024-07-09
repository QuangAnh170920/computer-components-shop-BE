package com.computercomponent.api.service.impl;

import com.computercomponent.api.dto.WarehouseDTO;
import com.computercomponent.api.dto.WarehouseManagementDTO;
import com.computercomponent.api.dto.WarehouseManagementStatusDTO;
import com.computercomponent.api.request.WarehouseRequest;
import com.computercomponent.api.response.WarehouseDetail;
import com.computercomponent.api.service.WarehouseService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class WarehouseServiceImpl implements WarehouseService {
    @Override
    public String create(WarehouseDTO warehouseDTO) {
        return "";
    }

    @Override
    public Page<WarehouseManagementDTO> getList(WarehouseRequest warehouseRequest) {
        return null;
    }

    @Override
    public String update(WarehouseManagementDTO warehouseManagementDTO) {
        return "";
    }

    @Override
    public String delete(Long id) {
        return "";
    }

    @Override
    public WarehouseDetail getDetail(Long id) {
        return null;
    }

    @Override
    public WarehouseManagementStatusDTO updateStatus(WarehouseManagementStatusDTO warehouseManagementStatusDTO) {
        return null;
    }
}
