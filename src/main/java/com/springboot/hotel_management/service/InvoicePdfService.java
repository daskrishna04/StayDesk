package com.springboot.hotel_management.service;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.springboot.hotel_management.entity.Client;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;

@Service
public class InvoicePdfService {

    public byte[] generateInvoice(Client client) throws Exception {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 50, 50, 60, 50);
        PdfWriter.getInstance(document, out);

        document.open();

        // ===== FONTS =====
        Font hotelFont = new Font(Font.HELVETICA, 24, Font.BOLD);
        Font sectionFont = new Font(Font.HELVETICA, 14, Font.BOLD);
        Font labelFont = new Font(Font.HELVETICA, 11, Font.BOLD);
        Font valueFont = new Font(Font.HELVETICA, 11);
        Font footerFont = new Font(Font.HELVETICA, 10, Font.ITALIC);

        // ===== HOTEL TITLE =====
        Paragraph hotelName = new Paragraph("THE GRAND LUXE HOTEL", hotelFont);
        hotelName.setAlignment(Element.ALIGN_CENTER);
        hotelName.setSpacingAfter(8);
        document.add(hotelName);

        Paragraph address = new Paragraph(
                "Luxury Stay • Premium Comfort • Exceptional Service",
                valueFont
        );
        address.setAlignment(Element.ALIGN_CENTER);
        address.setSpacingAfter(25);
        document.add(address);

        // ===== INVOICE TITLE =====
        Paragraph invoiceTitle = new Paragraph("INVOICE", sectionFont);
        invoiceTitle.setAlignment(Element.ALIGN_CENTER);
        invoiceTitle.setSpacingAfter(20);
        document.add(invoiceTitle);

        // ===== GUEST DETAILS TABLE =====
        PdfPTable details = new PdfPTable(2);
        details.setWidthPercentage(100);
        details.setSpacingAfter(25);
        details.setWidths(new float[]{1.5f, 2.5f});

        addRow(details, "Invoice No", "INV-2025-" + client.getClientId(), labelFont, valueFont);
//        addRow(details, "Reservation ID", String.valueOf(client.getReservationId()), labelFont, valueFont);
        addRow(details, "Guest Name", client.getClientName(), labelFont, valueFont);
//        addRow(details, "Room Number", String.valueOf(reservation.getRoom().getRoomNo()), labelFont, valueFont);
        addRow(details, "Check-In", client.getCheckInTime().toString(), labelFont, valueFont);
        addRow(details, "Check-Out", LocalDateTime.now().toString(), labelFont, valueFont);

        document.add(details);

        // ===== BILLING TABLE =====
        PdfPTable billing = new PdfPTable(3);
        billing.setWidthPercentage(100);
        billing.setWidths(new float[]{4f, 2f, 2f});
        billing.setSpacingAfter(20);

        addHeader(billing, "Description");
        addHeader(billing, "Days");
        addHeader(billing, "Amount (₹)");

        billing.addCell(cell("Luxury Room Charges", valueFont));
        billing.addCell(cell("1", valueFont));
//        billing.addCell(cell(String.valueOf(totalAmount), valueFont));

        document.add(billing);

        // ===== TOTAL =====
//        Paragraph total = new Paragraph(
//                "Total Amount Payable :  ₹ " + totalAmount,
//                new Font(Font.HELVETICA, 16, Font.BOLD)
//        );
//        total.setAlignment(Element.ALIGN_RIGHT);
//        total.setSpacingBefore(10);
//        document.add(total);

        // ===== FOOTER =====
        Paragraph footer = new Paragraph(
                "\nThank you for choosing The Grand Luxe Hotel.\n" +
                        "We look forward to welcoming you again.",
                footerFont
        );
        footer.setAlignment(Element.ALIGN_CENTER);
        footer.setSpacingBefore(40);
        document.add(footer);

        document.close();
        return out.toByteArray();
    }

    // ===== Helper Methods =====

    private void addRow(PdfPTable table, String label, String value,
                        Font labelFont, Font valueFont) {

        PdfPCell cell1 = new PdfPCell(new Phrase(label, labelFont));
        PdfPCell cell2 = new PdfPCell(new Phrase(value, valueFont));

        cell1.setBorder(Rectangle.NO_BORDER);
        cell2.setBorder(Rectangle.NO_BORDER);

        cell1.setPadding(6);
        cell2.setPadding(6);

        table.addCell(cell1);
        table.addCell(cell2);
    }

    private void addHeader(PdfPTable table, String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text,
                new Font(Font.HELVETICA, 12, Font.BOLD)));
        cell.setPadding(10);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(new Color(245, 247, 250));
        table.addCell(cell);
    }

    private PdfPCell cell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(10);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }
}

