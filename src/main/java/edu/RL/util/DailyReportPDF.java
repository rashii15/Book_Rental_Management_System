package edu.RL.util;

import com.itextpdf.layout.properties.UnitValue;
import com.lowagie.text.*;

import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPCell;

import java.awt.Color;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.lowagie.text.pdf.draw.LineSeparator;
import edu.RL.dto.RentalReportDTO;
import edu.RL.dto.RentalReportDTO;
import javafx.scene.text.TextAlignment;

public class DailyReportPDF {
    public void generate(List<RentalReportDTO> data, String filePath) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        Image logo = Image.getInstance("src/main/resources/img/original-922aedbea5c0b60dea18fe2f29d6b202-removebg-preview.png");
        logo.scaleToFit(100, 100);
        logo.setAlignment(Element.ALIGN_CENTER);
        document.add(logo);

        Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD, Color.getHSBColor(101, 67, 33));
        Paragraph title = new Paragraph("Daily Book Rental Report", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE);

        Paragraph address = new Paragraph("Panadura Library,\nNo.123, Main Rd, Panadura\nTel : 038 2248 543");
        address.setAlignment(Element.ALIGN_CENTER);
        document.add(address);
        document.add(Chunk.NEWLINE);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String currentTime = LocalTime.now().format(timeFormatter);

        PdfPTable table1 = new PdfPTable(2);
        table1.setTotalWidth(300);
        table1.setLockedWidth(true);
        Font headFont = new Font(Font.HELVETICA, 13, Font.BOLD, Color.getHSBColor(0, 0, 0));

        PdfPCell cell1 = new PdfPCell(new Phrase("Date",headFont));
        cell1.setBorder(Rectangle.NO_BORDER);
        table1.addCell(cell1);

        PdfPCell cell2 = new PdfPCell(new Phrase(String.valueOf(LocalDate.now())));
        cell2.setBorder(Rectangle.NO_BORDER);
        table1.addCell(cell2);

        PdfPCell cell3 = new PdfPCell(new Phrase("Time",headFont));
        cell3.setBorder(Rectangle.NO_BORDER);
        table1.addCell(cell3);

        PdfPCell cell4 = new PdfPCell(new Phrase(currentTime));
        cell4.setBorder(Rectangle.NO_BORDER);
        table1.addCell(cell4);

        table1.setHorizontalAlignment(Element.ALIGN_LEFT);
        document.add(table1);
        document.add(Chunk.NEWLINE);
        LineSeparator line = new LineSeparator();
        line.setLineColor(new Color(0, 0, 0)); // black line
        line.setLineWidth(1f);
        document.add(new Chunk(line));

        document.add(Chunk.NEWLINE);

        if (data == null || data.isEmpty()) {
            document.add(new Paragraph("No rentals today."));
        } else {
            document.add(Chunk.NEWLINE);
            PdfPTable table = new PdfPTable(8);
            table.setWidthPercentage(100);

            String[] headers = {"Rental ID", "Book Title", "User Name", "Rental Date",
                    "Due Date", "Return Date", "Status", "Fine"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header));
                cell.setBackgroundColor(Color.LIGHT_GRAY);
                table.addCell(cell);
            }

            for (RentalReportDTO r : data) {
                table.addCell(nullSafe(r.getRentalId()));
                table.addCell(nullSafe(r.getBookTitle()));
                table.addCell(nullSafe(r.getUserName()));
                table.addCell(nullSafe(r.getRentalDate()));
                table.addCell(nullSafe(r.getDueDate()));
                table.addCell(nullSafe(r.getReturnDate()));
                PdfPCell statusCell = new PdfPCell(new Phrase(nullSafe(r.getStatus())));
                if ("Overdue".equalsIgnoreCase(r.getStatus())) {
                    statusCell.setBackgroundColor(new Color(255, 200, 200));
                }
                table.addCell(statusCell);
                table.addCell(String.valueOf(r.getFine()));
            }

            document.add(table);
        }
        document.add(Chunk.NEWLINE);
        double totalFine = 0.0;
        if (data != null) {
            for (RentalReportDTO r : data) {
                totalFine += r.getFine(); // assuming getFine() returns a double
            }
        }
        document.add(new Chunk(line));
        document.add(Chunk.NEWLINE);
        Paragraph total = new Paragraph("Total Fine: " + totalFine);
        total.setAlignment(Element.ALIGN_RIGHT); // align right
        document.add(total);
        document.add(new Chunk(line));
        document.add(Chunk.NEWLINE);
        Font prFont = new Font(Font.HELVETICA, 15, Font.BOLD, Color.getHSBColor(0, 0, 0));
        Paragraph pr= new Paragraph("This is today's Rental and Return history Report",prFont);
        pr.setAlignment(Element.ALIGN_CENTER);
        document.add(pr);

        document.close();
        System.out.println("Daily report generated at: " + filePath);
    }

    private String nullSafe(String s) {
        return s == null ? "-" : s;
    }
}
