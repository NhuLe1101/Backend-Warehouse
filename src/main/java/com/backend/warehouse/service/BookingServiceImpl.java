package com.backend.warehouse.service;

import com.backend.warehouse.entity.Booking;
import com.backend.warehouse.entity.Item;
import com.backend.warehouse.repository.BookingRepository;
import com.backend.warehouse.repository.ItemRepository;

import io.jsonwebtoken.io.IOException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BookingServiceImpl {

    @Autowired
    private BookingRepository bookingRepository;
    
	  @Autowired
	  private ItemRepository itemRepository;
    
    private String[] header = {"Name","Type","Quantity","Weight (g)","Checkin Date","Checkout Date","Image", "Useremail"};
    
    private final String uploadDir = "uploads/";

    public Booking saveData(Booking booking) {
        return bookingRepository.save(booking);
    }
    
    Path uploadPath = Paths.get(uploadDir);
    
	  public void saveFormData(String email, String phoneNumber, String fullName, MultipartFile file) throws IOException, java.io.IOException {
	  
	  if (!Files.exists(uploadPath)) {
	      Files.createDirectories(uploadPath);
	  }
	
	  String originalFileName = file.getOriginalFilename();
	  String fileName = originalFileName;
	  Path filePath = uploadPath.resolve(fileName);
	
	  while (Files.exists(filePath)) {
	      String newFileName = String.format("%s_%s.%s", 
	          originalFileName.substring(0, originalFileName.lastIndexOf(".")), 
	          LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")), 
	          originalFileName.substring(originalFileName.lastIndexOf(".") + 1));
	      filePath = uploadPath.resolve(newFileName);
	  }
	
	  Files.copy(file.getInputStream(), filePath);
	
	  Booking booking = new Booking();
	  booking.setCustomerEmail(email);
	  booking.setNumberphone(phoneNumber);
	  booking.setCustomerName(fullName);
	  booking.setExcelFile(filePath.toString());
	  
	  Booking savedBooking = bookingRepository.save(booking);
	  System.out.println("File đã được lưu vào: " + filePath.toString());
	  
    if (file != null && !file.isEmpty()) {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
         CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.builder().setHeader(header).setSkipHeaderRecord(true).build())) {
         
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (CSVRecord csvRecord : csvParser) {
            Item item = new Item();
            item.setName(csvRecord.get("Name"));
            item.setType(csvRecord.get("Type"));
            item.setQuantity(Integer.parseInt(csvRecord.get("Quantity").replace(".", "")));
            item.setWeight(Float.parseFloat(csvRecord.get("Weight (g)").replace(".", "")));
            item.setCheckin(LocalDate.parse(csvRecord.get("Checkin Date"), formatter));
            item.setCheckout(LocalDate.parse(csvRecord.get("Checkout Date"), formatter));
            item.setImage(null);
            item.setBooking(savedBooking);
            item.setShelf(null);
            item.setStatus("Đang lưu kho");
            item.setDelivery(null);
            itemRepository.save(item);
        }
    }
}
	
	}
    
	 public List<Booking> getAllBookings() {
	  return bookingRepository.findAll();
	}
}
