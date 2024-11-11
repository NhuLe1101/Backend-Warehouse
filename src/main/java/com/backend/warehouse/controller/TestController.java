package com.backend.warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.warehouse.scheduler.CheckoutReminderJob;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private CheckoutReminderJob checkoutReminderJob;

    @GetMapping("/test-reminder-job")
    public String testReminderJob() {
        checkoutReminderJob.checkItemsForCheckoutReminder();
        return "Reminder job executed";
    }
}
