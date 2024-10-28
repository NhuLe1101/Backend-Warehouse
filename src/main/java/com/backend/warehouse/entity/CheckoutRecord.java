package com.backend.warehouse.entity;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "checkoutrecord")
public class CheckoutRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Item item;

    @ManyToOne
    private Compartment compartment;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(nullable = true)
    private String referenceNo;
    
    @Column(nullable = true)
    private String delivery;
    
    @Column(nullable = true)
    private LocalDate checkoutDate;
    
    @Column(nullable = true)
    private boolean confirmed;
    
    @Column(nullable = false) // Thêm cột quantity để lưu số lượng khi checkout
    private int quantity;
    
    @Column(nullable = true)
    private Long storageDuration;
    
    public CheckoutRecord() {
    	
    }

	public CheckoutRecord(Item item, Compartment compartment, User user, String referenceNo, String delivery,
			LocalDate checkoutDate, boolean confirmed, int quantity, Long storageDuration) {
		super();
		this.item = item;
		this.compartment = compartment;
		this.user = user;
		this.referenceNo = referenceNo;
		this.delivery = delivery;
		this.checkoutDate = checkoutDate;
		this.confirmed = confirmed;
		this.quantity = quantity;
		this.storageDuration = storageDuration;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Compartment getCompartment() {
		return compartment;
	}

	public void setCompartment(Compartment compartment) {
		this.compartment = compartment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(LocalDate checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Long getStorageDuration() {
        return storageDuration;
    }

    public void setStorageDuration(Long storageDuration) {
        this.storageDuration = storageDuration;
    }
}
