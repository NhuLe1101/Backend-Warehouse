package com.backend.warehouse.service;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.backend.warehouse.entity.Booking;
import com.backend.warehouse.entity.CheckoutRecord;
import com.backend.warehouse.entity.Item;
import net.sf.jasperreports.engine.JRException;

import com.backend.warehouse.payload.request.BookingReport;
import com.backend.warehouse.payload.request.BookingReportDataClient;
import com.backend.warehouse.payload.request.DeliveryReport;

public interface ReportService {
	
	byte[] generatePdfReportItem(List<Item> items) throws JRException, FileNotFoundException;
	
	byte[] generatePdfDeliveryReport(DeliveryReport items) throws JRException, FileNotFoundException;
	
	byte[] generatePdfBooking(BookingReportDataClient booking) throws JRException, FileNotFoundException;
	
	byte[] generatePdfCheckoutItem(List<CheckoutRecord> checkoutRecords) throws JRException, FileNotFoundException;
	
	Map<LocalDate, List<CheckoutRecord>> getCheckoutRecordsGroupedByDate();
}
