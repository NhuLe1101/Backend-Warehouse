package com.backend.warehouse.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.warehouse.entity.Booking;
import com.backend.warehouse.payload.response.BookingResponse;
import com.backend.warehouse.payload.response.MessageResponse;
import com.backend.warehouse.service.BookingServiceImpl;
import io.jsonwebtoken.io.IOException;
import com.backend.warehouse.service.ItemServiceImpl;

@CrossOrigin(origins = {"http://localhost:3000", "https://webapp-warehouse3d.netlify.app"}, maxAge = 3600)
@RestController
@RequestMapping("/api/booking")
public class BookingController {

	@Autowired
	private BookingServiceImpl bookingService;

	@Autowired
	private ItemServiceImpl itemService;

	@PostMapping("/upload")
	public ResponseEntity<?> uploadFormData(@RequestParam("file") MultipartFile file) {
		try {
			bookingService.saveFormData(file);
			return ResponseEntity.ok(new MessageResponse("Dữ liệu đã được lưu thành công!"));
		} catch (IOException e) {
			return ResponseEntity.status(500)
					.body(new MessageResponse("Có lỗi xảy ra khi lưu dữ liệu: " + e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(400).body(new MessageResponse("Dữ liệu không hợp lệ: " + e.getMessage()));
		}
	}

	@GetMapping("/all")
	public ResponseEntity<List<BookingResponse>> getAllBookings() {
		List<BookingResponse> bookings = bookingService.getAllBookings();
		return ResponseEntity.ok(bookings);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateBooking(
      @PathVariable("id") String id,
      @RequestParam("email") String email,
      @RequestParam("phoneNumber") String phoneNumber,
      @RequestParam("fullName") String fullName,
      @RequestParam("filePath") String filePath
	) {
      try {
          bookingService.updateBooking(id, email, phoneNumber, fullName, filePath);
          return ResponseEntity.ok(new MessageResponse("Cập nhật booking thành công!"));
      } catch (IOException e) {
          return ResponseEntity
              .status(500) 
              .body(new MessageResponse("Có lỗi xảy ra khi cập nhật dữ liệu: " + e.getMessage()));
      } catch (Exception e) {
          return ResponseEntity
              .status(400) 
              .body(new MessageResponse("Dữ liệu không hợp lệ: " + e.getMessage()));
      }
  }
	
  @GetMapping("/download/{fileName:.+}")
  public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
      try {
          Path file = Paths.get("uploads").resolve(fileName);
          Resource resource = new UrlResource(file.toUri());

          if (resource.exists() || resource.isReadable()) {
              return ResponseEntity.ok()
                  .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                  .body(resource);
          } else {
              throw new RuntimeException("Không thể đọc file: " + fileName);
          }
      } catch (Exception e) {
          throw new RuntimeException("Có lỗi xảy ra khi tải file: " + fileName, e);
      }
  }

  @GetMapping("/totalCustomers")
  public ResponseEntity<Long> getTotalCustomers() {
      Long totalCustomers = bookingService.getTotalCustomers();
      return ResponseEntity.ok(totalCustomers);
  }
  
	public Long parseId(String formattedId) {
	    if (formattedId.startsWith("BK")) {
	        String numericPart = formattedId.substring(2); 
	        return Long.parseLong(numericPart);
	    } else {
	        throw new IllegalArgumentException("Invalid formatted ID: " + formattedId);
	    }
	}
	
  
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> deleteBooking(@PathVariable("id") String id) {
      try {
          itemService.deleteItemsByBookingId(parseId(id));

          bookingService.deleteBookingById(parseId(id));

          return ResponseEntity.ok(new MessageResponse("Xóa booking và các item liên quan thành công!"));
      } catch (Exception e) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(new MessageResponse("Có lỗi xảy ra khi xóa booking: " + e.getMessage()));
      }
  }

  
}
