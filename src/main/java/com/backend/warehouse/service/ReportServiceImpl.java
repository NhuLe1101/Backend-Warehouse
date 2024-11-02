package com.backend.warehouse.service;

import com.backend.warehouse.entity.Item;
import com.backend.warehouse.payload.request.ReportRequest;
import com.backend.warehouse.repository.CheckoutRecordRepository;
import com.backend.warehouse.repository.ItemRepository;
import com.backend.warehouse.payload.request.BookingReport;
import com.backend.warehouse.payload.request.BookingReportDataClient;
import com.backend.warehouse.payload.request.CheckoutItemReportPrintTemplate;
import com.backend.warehouse.payload.request.DeliveryReport;
import com.backend.warehouse.entity.Booking;
import com.backend.warehouse.entity.CheckoutRecord;
import com.backend.warehouse.payload.request.DeliveryReportPrintTemplate;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.xml.JRPenFactory.Style;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.tomcat.util.buf.Utf8Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ItemRepository itemRepository;
    
    @Autowired
    private CheckoutRecordRepository checkoutRecordRepository;

    public Map<LocalDate, List<CheckoutRecord>> getCheckoutRecordsGroupedByDate() {
        List<CheckoutRecord> records = checkoutRecordRepository.findAll();
        return records.stream()
        		.filter(record -> record.isConfirmed()) 
                .collect(Collectors.groupingBy(CheckoutRecord::getCheckoutDate));
    }
    
    public byte[] generatePdfReportItem(List<Item> items) throws JRException, FileNotFoundException {
        List<ReportRequest> reportItems = items.stream()
            .map(item -> new ReportRequest(
                item.getName(),
                item.getQuantity(),
            	java.sql.Date.valueOf(item.getCheckin()),
            	java.sql.Date.valueOf(item.getCheckout()),
            	item.getDelivery(),
            	item.getBooking().getId()
            ))
            .collect(Collectors.toList());

//        reportItems.forEach(reportItem -> 
//            System.out.println("ReportItem: Name = " + reportItem.getName() + 
//                               ", Quantity = " + reportItem.getQuantity() + 
//                               ", Checkout = " + reportItem.getCheckout())
//        );

        
        String path = ResourceUtils.getFile("classpath:reports/ReportVip5.jrxml").getAbsolutePath();
        JasperReport jasperReport = JasperCompileManager.compileReport(path);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportItems);
        Map<String, Object> parameters = new HashMap<>();
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        
        return outputStream.toByteArray();
    }
    
    
    /*
     * 	DeliveryReportPrintTemplate(String productName, Long quantity, float weight, String type, String nameComp,
			String nameShelf, Long storageDuration, String employeeName, String delivery, Long referenceNo,
			Date checkout) {
     * */
    
    
    public byte[] generatePdfDeliveryReport(DeliveryReport items) throws JRException, FileNotFoundException {
        List<DeliveryReportPrintTemplate> reportItems = items.getItems().stream()
            .map(item -> new DeliveryReportPrintTemplate(
                item.getItem().getName(),
                item.getQuantity(),
                item.getItem().getWeight(),
                item.getItem().getType(),
                item.getCompartment().getNameComp(),
                item.getCompartment().getShelf().getNameShelf(),
                item.getStorageDuration(),
                item.getUser().getProfileName(),
                item.getDelivery(),
                item.getReferenceNo(),
            	java.sql.Date.valueOf(item.getCheckoutDate())
            ))
            .collect(Collectors.toList());

        reportItems.forEach(reportItem -> 
            System.out.println("ReportItem: " + 
                               "Name = " + reportItem.getProductName() + 
                               ", Quantity = " + reportItem.getQuantity() + 
                               ", Weight = " + reportItem.getWeight() + 
                               ", Type = " + reportItem.getType() + 
                               ", Compartment = " + reportItem.getNameComp() + 
                               ", Shelf = " + reportItem.getNameShelf() + 
                               ", Storage Duration = " + reportItem.getStorageDuration() + 
                               ", User = " + reportItem.getEmployeeName() + 
                               ", Delivery = " + reportItem.getDelivery() + 
                               ", Reference No = " + reportItem.getReferenceNo() + 
                               ", Checkout Date = " + reportItem.getCheckout()
                               ));

        
        String path = ResourceUtils.getFile("classpath:reports/DeliveryReport_v25.jrxml").getAbsolutePath();
        JasperReport jasperReport = JasperCompileManager.compileReport(path);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportItems);
        Map<String, Object> parameters = new HashMap<>();
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        
        return outputStream.toByteArray();
    }
    
    public byte[] generatePdfBooking(BookingReportDataClient booking) throws JRException, FileNotFoundException {
    	
    	String customerEmail = booking.getCustomerEmail();
    	String customerName = booking.getCustomerName();
    	String numberphone = booking.getNumberphone();
    	Long referenceNo = booking.getReferenceNo();
    	
    	BookingReport bookingReport = new BookingReport(customerEmail, customerName, numberphone, referenceNo);
        System.out.println("Booking Data: " + bookingReport.toString());

        String path = ResourceUtils.getFile("classpath:reports/Booking_v8.jrxml").getAbsolutePath();
        
        JasperReport jasperReport = JasperCompileManager.compileReport(path);
        
        List<BookingReport> bookingList = Collections.singletonList(bookingReport);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(bookingList);
        
        Map<String, Object> parameters = new HashMap<>();
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        
        return outputStream.toByteArray();
    }

    public byte[] generatePdfCheckoutItem(List<CheckoutRecord> checkoutRecords) throws JRException, FileNotFoundException {
        List<CheckoutItemReportPrintTemplate> reportItems = checkoutRecords.stream()
            .map(item -> new CheckoutItemReportPrintTemplate(
                item.getItem().getName(),
                item.getQuantity(),
                item.getItem().getWeight(),
                item.getItem().getType(),
                item.getCompartment().getNameComp(),
                item.getCompartment().getShelf().getNameShelf(),
                item.getStorageDuration(),
            	java.sql.Date.valueOf(item.getCheckoutDate()),
            	item.getDelivery(),
            	item.getReferenceNo(),
            	item.getUser().getProfileName()
            ))
            .collect(Collectors.toList());

        reportItems.forEach(reportItem -> 
            System.out.println("ReportItem: " + 
                               "Name = " + reportItem.getProductName() + 
                               ", Quantity = " + reportItem.getQuantity() + 
                               ", Weight = " + reportItem.getWeight() + 
                               ", Type = " + reportItem.getType() + 
                               ", Compartment = " + reportItem.getNameComp() + 
                               ", Shelf = " + reportItem.getNameShelf() + 
                               ", Storage Duration = " + reportItem.getStorageDuration() + 
                               ", Checkout Date = " + reportItem.getCheckout()
                               ));

        
        String path = ResourceUtils.getFile("classpath:reports/CheckoutItemReport_v14.jrxml").getAbsolutePath();
        JasperReport jasperReport = JasperCompileManager.compileReport(path);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportItems);
        Map<String, Object> parameters = new HashMap<>();
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        
        return outputStream.toByteArray();
    }
    
}
