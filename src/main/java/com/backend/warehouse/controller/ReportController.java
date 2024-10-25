package com.backend.warehouse.controller;

import com.backend.warehouse.entity.Item;
import com.backend.warehouse.repository.ItemRepository;
import com.backend.warehouse.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/jasper")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/generate-pdf")
    public ResponseEntity<byte[]> generatePdf() {
        try {
            // Lấy danh sách item từ repository
            List<Item> items = itemRepository.findAll();

            // Gọi service để tạo báo cáo PDF
            byte[] pdfContent = reportService.generatePdfReport(items);

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
}
