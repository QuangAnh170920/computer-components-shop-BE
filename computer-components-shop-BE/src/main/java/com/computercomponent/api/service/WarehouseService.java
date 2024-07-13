package com.computercomponent.api.service;

import com.computercomponent.api.dto.WarehouseDTO;
import com.computercomponent.api.dto.WarehouseManagementDTO;
import com.computercomponent.api.dto.WarehouseManagementStatusDTO;
import com.computercomponent.api.dto.WarehouseUpdateRequestDTO;
import com.computercomponent.api.request.WarehouseRequest;
import com.computercomponent.api.response.WarehouseDetail;
import org.springframework.data.domain.Page;

public interface WarehouseService {
    String create(WarehouseDTO warehouseDTO);

    Page<WarehouseManagementDTO> getList(WarehouseRequest warehouseRequest);

    WarehouseUpdateRequestDTO update(WarehouseUpdateRequestDTO warehouseUpdateRequestDTO);

    String delete(Long id);

    WarehouseDetail getDetail(Long id);

    WarehouseManagementStatusDTO updateStatus(WarehouseManagementStatusDTO warehouseManagementStatusDTO);
}
