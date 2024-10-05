package com.backend.warehouse.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.warehouse.entity.Warehouse;
import com.backend.warehouse.repository.WarehouseRepository;

@Service
public class WarehouseServiceImpl implements WarehouseService {

	@Autowired
    private WarehouseRepository warehouseRepository;

    @Override
    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    @Override
    public Warehouse addWarehouse(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    @Override
    public Optional<Warehouse> getWarehouseById(Long id) {
        return warehouseRepository.findById(id);
    }

    @Override
    public Warehouse updateWarehouse(Long id, Warehouse warehouse) {
        return warehouseRepository.findById(id)
            .map(existingWarehouse -> {
                existingWarehouse.setName(warehouse.getName());
                existingWarehouse.setLocation(warehouse.getLocation());
                return warehouseRepository.save(existingWarehouse);
            })
            .orElseThrow(() -> new RuntimeException("Warehouse not found with id " + id));
    }

    @Override
    public void deleteWarehouse(Long id) {
        warehouseRepository.deleteById(id);
    }

}
