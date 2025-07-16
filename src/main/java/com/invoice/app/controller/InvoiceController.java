package com.invoice.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.invoice.app.model.*;
import java.util.stream.Collectors;

import com.invoice.app.model.Invoice;
import com.invoice.app.service.*;

@Controller
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("invoice", new Invoice());
        return "invoice-form";
    }

    @PostMapping("/generate")
    public String generate(@ModelAttribute Invoice invoice, Model model) throws Exception {
        String filePath = invoiceService.generatePdf(invoice);
        invoiceService.sendEmailWithAttachment(invoice.getClientEmail(), filePath);

        double total = 0;
        
        if (invoice.getItems() != null) {
            for (InvoiceItem item : invoice.getItems()) {
                total += item.getQuantity() * item.getPrice();
            }
        }
        model.addAttribute("message", "Invoice generated and emailed successfully.");
        model.addAttribute("total", total);
        return "result";  // ✅ No special character
    }
}

