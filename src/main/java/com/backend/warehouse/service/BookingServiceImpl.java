package com.backend.warehouse.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backend.warehouse.entity.Booking;
import com.backend.warehouse.entity.Item;
import com.backend.warehouse.repository.BookingRepository;
import com.backend.warehouse.repository.ItemRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

@Service
public class BookingServiceImpl {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ItemRepository itemRepository;
    

    private String[] header = {"Name","Type","Quantity","Checkin Date","Checkout Date","Image","Useremail"};

    private final String uploadDir = "uploads/";

    public void saveFormData(String email, String phoneNumber, String fullName, String delivery, MultipartFile file, String status, LocalDate checkin, LocalDate checkout) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFileName = file.getOriginalFilename();
        String fileName = originalFileName;
        Path filePath = uploadPath.resolve(fileName);

        int count = 1;
        while (Files.exists(filePath)) {
            String newFileName = String.format("%s_%s.%s", 
                originalFileName.substring(0, originalFileName.lastIndexOf(".")), 
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")), 
                originalFileName.substring(originalFileName.lastIndexOf(".") + 1));
            filePath = uploadPath.resolve(newFileName);
            count++;
        }

        Files.copy(file.getInputStream(), filePath);

        Booking booking = new Booking();
        booking.setCustomerEmail(email);
        booking.setNumberphone(phoneNumber);
        booking.setCustomerName(fullName);
//        booking.setDelivery(delivery);
        booking.setExcelFile(filePath.toString());
//        booking.setStatus(status);
//        booking.setCheckIn(checkin);
//        booking.setCheckOut(checkout);
        
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
                    item.setCheckin(LocalDate.parse(csvRecord.get("Checkin Date"), formatter));
                    item.setCheckout(LocalDate.parse(csvRecord.get("Checkout Date"), formatter));
                    item.setImage(csvRecord.get("Image"));
                    item.setBooking(savedBooking);
//                    item.setaPackage(null);
//                    item.setDimension(null);
//                    item.setPosition(null);
                    item.setShelf(null);
                    item.setWeight(0);
                    itemRepository.save(item);
                }
            }
        }
    }

    
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
    
    public Booking updateBooking(Long id, String email, String phoneNumber, String fullName, String delivery , String status) throws IOException {
        Booking booking = bookingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy booking với ID: " + id));
        
        booking.setCustomerEmail(email);
        booking.setNumberphone(phoneNumber);
        booking.setCustomerName(fullName);
//        booking.setDelivery(delivery);
//        booking.setStatus(status);

        return bookingRepository.save(booking);
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }




}
