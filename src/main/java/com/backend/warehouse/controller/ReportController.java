package com.backend.warehouse.controller;

import com.backend.warehouse.entity.Booking;
import com.backend.warehouse.entity.CheckoutRecord;
import com.backend.warehouse.entity.Item;
import com.backend.warehouse.repository.ItemRepository;
import com.backend.warehouse.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.export.oasis.Style;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.warehouse.payload.request.BookingReport;
import com.backend.warehouse.payload.request.BookingReportDataClient;
import com.backend.warehouse.payload.request.BookingReportDataClient_IdString;
import com.backend.warehouse.payload.request.DeliveryReport;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/jasper")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private ItemRepository itemRepository;
    

    @GetMapping("/checkout-records/grouped")
    public Map<LocalDate, List<CheckoutRecord>> getGroupedCheckoutRecords() {
        return reportService.getCheckoutRecordsGroupedByDate();
    }
    
    @GetMapping("/generate-pdf-item")
    public ResponseEntity<byte[]> generatePdf() {
        try {
            // Lấy danh sách item từ repository
            List<Item> items = itemRepository.findAll();

            // Gọi service để tạo báo cáo PDF
            byte[] pdfContent = reportService.generatePdfReportItem(items);

            // Tạo header cho phản hồi
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=report.pdf");
            headers.setContentType(MediaType.APPLICATION_PDF);

            return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
        } catch (FileNotFoundException e) {
            // Trả về một mảng byte rỗng khi không tìm thấy file
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new byte[0]);
        } catch (JRException e) {
            // Trả về một mảng byte rỗng khi xảy ra lỗi trong quá trình tạo báo cáo
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new byte[0]);
        }
    }
    
    @PostMapping("/generate-pdf-delivery-report")
    public ResponseEntity<byte[]> generatePdfDeliveryReport(@RequestBody DeliveryReport deliveryReportData) {
        try {

            byte[] pdfContent = reportService.generatePdfDeliveryReport(deliveryReportData);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=report.pdf");
            headers.setContentType(MediaType.APPLICATION_PDF);

            return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new byte[0]);
        } catch (JRException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new byte[0]);
        }
    }
    
	public Long parseId(String formattedId) {
	    if (formattedId.startsWith("BK")) {
	        String numericPart = formattedId.substring(2); 
	        return Long.parseLong(numericPart);
	    } else {
	        throw new IllegalArgumentException("Invalid formatted ID: " + formattedId);
	    }
	}
    
    @PostMapping("/generate-pdf-booking")
    public ResponseEntity<byte[]> generatePdfBooking(@RequestBody BookingReportDataClient_IdString bookingReportDataIdString) {
        try {
        	
        	BookingReportDataClient bookingReportData = new BookingReportDataClient(
        			parseId(bookingReportDataIdString.getId()),
        			bookingReportDataIdString.getCustomerEmail(),
        			bookingReportDataIdString.getCustomerName(),
        			bookingReportDataIdString.getNumberphone(),
        			bookingReportDataIdString.getExcelFile(),
        			bookingReportDataIdString.getReferenceNo()
        	);
        	
            byte[] pdfContent = reportService.generatePdfBooking(bookingReportData);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=report.pdf");
            headers.setContentType(MediaType.APPLICATION_PDF);

            return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new byte[0]);
        } catch (JRException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new byte[0]);
        }
    }
    
    @PostMapping("/generate-pdf-checkout-item")
    public ResponseEntity<byte[]> generatePdfCheckoutItem(@RequestBody List<CheckoutRecord> checkoutRecords) {
        try {
        	
            byte[] pdfContent = reportService.generatePdfCheckoutItem(checkoutRecords);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=report.pdf");
            headers.setContentType(MediaType.APPLICATION_PDF);

            return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new byte[0]);
        } catch (JRException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new byte[0]);
        }
    }
    
}
