package com.backend.warehouse.service;

import com.backend.warehouse.entity.Item;
import com.backend.warehouse.payload.request.ReportRequest;
import com.backend.warehouse.repository.ItemRepository;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ItemRepository itemRepository;
    
    public byte[] generatePdfReport(List<Item> items) throws JRException, FileNotFoundException {
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


        String path = ResourceUtils.getFile("classpath:reports/Report_test_11.jrxml").getAbsolutePath();
        JasperReport jasperReport = JasperCompileManager.compileReport(path);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportItems);
        Map<String, Object> parameters = new HashMap<>();
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        
        return outputStream.toByteArray();
    }
}
