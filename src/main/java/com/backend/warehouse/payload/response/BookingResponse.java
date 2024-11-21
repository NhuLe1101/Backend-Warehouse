package com.backend.warehouse.payload.response;

public class BookingResponse {
    private String id; 
    private String customerEmail;
    private String customerName;
    private String numberphone;
    private String excelFile;
    private Long referenceNo; 

    public BookingResponse(String id, String customerEmail, String customerName, String numberphone, String excelFile, Long referenceNo) {
        this.id = id;
        this.customerEmail = customerEmail;
        this.customerName = customerName;
        this.numberphone = numberphone;
        this.excelFile = excelFile;
        this.referenceNo = referenceNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Long getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(Long referenceNo) {
        this.referenceNo = referenceNo;
    }
}
