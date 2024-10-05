package com.backend.warehouse.service;

import java.util.List;
import java.util.Optional;

import com.backend.warehouse.entity.Warehouse;

public interface WarehouseService {
    List<Warehouse> getAllWarehouses();
    Warehouse addWarehouse(Warehouse warehouse);
    Optional<Warehouse> getWarehouseById(Long id);
    Warehouse updateWarehouse(Long id, Warehouse warehouse);
    void deleteWarehouse(Long id);
}
