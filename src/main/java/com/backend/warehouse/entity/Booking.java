package com.backend.warehouse.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String customerEmail;
    
    @Column(nullable = true)
    private String customerName;
    
    @Column(nullable = true)
    private String numberphone;
    
    @Column(nullable = false)
    private String excelFile; 
    

    public Booking() {
    	
    }


	public Booking(String customerEmail, String customerName, String numberphone, String excelFile) {
		super();
		this.customerEmail = customerEmail;
		this.customerName = customerName;
		this.numberphone = numberphone;
		this.excelFile = excelFile;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getCustomerEmail() {
		return customerEmail;
	}


	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}


	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public String getNumberphone() {
		return numberphone;
	}


	public void setNumberphone(String numberphone) {
		this.numberphone = numberphone;
	}


	public String getExcelFile() {
		return excelFile;
	}


	public void setExcelFile(String excelFile) {
		this.excelFile = excelFile;
	}
    
    

    
}