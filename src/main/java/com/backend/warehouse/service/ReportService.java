package com.backend.warehouse.service;

import java.io.FileNotFoundException;
import java.util.List;
import com.backend.warehouse.entity.Item;
import net.sf.jasperreports.engine.JRException;

public interface ReportService {
	
	byte[] generatePdfReport(List<Item> items) throws JRException, FileNotFoundException;

}
