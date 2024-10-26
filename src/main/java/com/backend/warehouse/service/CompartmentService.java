package com.backend.warehouse.service;

import java.util.List;
import java.util.Optional;

import com.backend.warehouse.entity.CheckoutRecord;
import com.backend.warehouse.entity.Compartment;
import com.backend.warehouse.entity.Shelf;
import com.backend.warehouse.payload.response.MessageResponse;

public interface CompartmentService {
	List<Compartment> getAllCompartments();
	
	Compartment getCompartmentById(Long compartmentId);
	
	Compartment getCompartment(Long shelfId, String nameComp);

	Compartment saveCompartment(Long shelfId, Compartment compartment);

	MessageResponse addItemToCompartment(Long compartmentId, Long itemId, int quantity);

	MessageResponse updateItemQuantity(Long compartmentId, Long itemId, int quantity);
	
	MessageResponse removeItemFromCompartment(Long compartmentId, Long itemId);

    MessageResponse checkoutItem(Long compartmentId, Long itemId, String referenceNo, String delivery);
	
    List<CheckoutRecord> getPendingCheckoutItems();

    MessageResponse confirmCheckout(Long recordId);

    MessageResponse cancelCheckout(Long recordId);
}
