package com.backend.warehouse.service;

import java.util.List;
import java.util.Optional;
import com.backend.warehouse.entity.Compartment;

public interface CompartmentService {
    Compartment saveCompartment(Long shelfId, Compartment compartment);
}
