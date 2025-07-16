package com.invoice.app.service;

import com.invoice.app.model.Invoice;
import com.invoice.app.model.InvoiceItem;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import java.io.FileOutputStream;
import java.util.List;

@Service
public class InvoiceService {

    @Autowired
    private JavaMailSender mailSender;
    public String generatePdf(Invoice invoice) throws Exception {
        String filePath = "invoice_" + System.currentTimeMillis() + ".pdf";

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        document.add(new Paragraph("INVOICE", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
        document.add(new Paragraph(" "));

        document.add(new Paragraph("Client Name: " + invoice.getClientName()));
        document.add(new Paragraph("Client Email: " + invoice.getClientEmail()));
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.addCell("Description");
        table.addCell("Quantity");
        table.addCell("Price");
        double total = 0;
        List<InvoiceItem> items = invoice.getItems();
        for (InvoiceItem item : items) {
            table.addCell(item.getDescription());
            table.addCell(String.valueOf(item.getQuantity()));
            table.addCell(String.valueOf(item.getPrice()));
            total += item.getQuantity() * item.getPrice();
        }

        document.add(table);
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Total Amount: ₹" + total));

        document.close();
        return filePath;
    }

    // ✅ Email method with try-catch and debug logs
    public void sendEmailWithAttachment(String to, String filePath) {
        try {
            System.out.println("Attempting to send email to: " + to);
            System.out.println("PDF file path: " + filePath);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject("Invoice from Java App");
            helper.setText("Please find your invoice attached.");
            helper.addAttachment("invoice.pdf", new java.io.File(filePath));

            mailSender.send(message);
            System.out.println("✅ Email sent successfully to " + to);
        } catch (Exception e) {
            System.out.println("❌ Failed to send email to " + to);
            e.printStackTrace(); // ✅ This line is fine
        }
    }
}
