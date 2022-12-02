package com.company;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class Invoices {

    public static String GenerateAllCarsInvoice(ArrayList<Car> list) throws FileNotFoundException, DocumentException {
        Invoice invoice = new Invoice("firma sprzedajaca auta","nabywca",list);

        Document document = new Document(); // dokument pdf
        String path = "invoices/invoice_all_cars_"+invoice.getTitle()+".pdf";
        PdfWriter.getInstance(document, new FileOutputStream(path));

        document.open();

        generateInvoiceModel(invoice.getTitle(), invoice.getSeller(), invoice.getBuyer(),"Faktura za wszystkie auta",document );

        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Paragraph p = new Paragraph("", font);
        int licznik = 1;
        PdfPTable table = new PdfPTable(4);
        table.addCell(new PdfPCell(new Phrase("lp",font)));
        table.addCell(new PdfPCell(new Phrase("cena",font)));
        table.addCell(new PdfPCell(new Phrase("vat",font)));
        table.addCell(new PdfPCell(new Phrase("wartosc",font)));
        for(Car car : list){
            table.addCell(new PdfPCell(new Phrase(licznik + "",font)));
            table.addCell(new PdfPCell(new Phrase(car.getCena()+"",font)));
            table.addCell(new PdfPCell(new Phrase(car.getVat() + "%",font)));
            table.addCell(new PdfPCell(new Phrase(invoice.CalcVat(car)+"",font)));
            licznik+=1;
        }
        document.add(table);
        font = FontFactory.getFont(FontFactory.COURIER_BOLD, 16, BaseColor.BLACK);

        document.add(new Chunk("DO ZAPLATY " + invoice.SumPrice() + " PLN", font));
        document.add(p);

        document.close();
        return invoice.getTitle();
    }
    public static String GenerateYearCarsInvoice(ArrayList<Car> list,String year) throws FileNotFoundException, DocumentException {
        Invoice invoice = new Invoice("firma sprzedajaca auta","nabywca",list);

        Document document = new Document(); // dokument pdf
        String path = "invoices/invoice_year_cars_"+invoice.getTitle()+".pdf";
        PdfWriter.getInstance(document, new FileOutputStream(path));

        document.open();
        generateInvoiceModel(invoice.getTitle(), invoice.getSeller(), invoice.getBuyer(),"Faktura za auta z roku: " + year ,document );



        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Paragraph p = new Paragraph("", font);
        int licznik = 1;
        PdfPTable table = new PdfPTable(5);
        table.addCell(new PdfPCell(new Phrase("lp",font)));
        table.addCell(new PdfPCell(new Phrase("rok",font)));
        table.addCell(new PdfPCell(new Phrase("cena",font)));
        table.addCell(new PdfPCell(new Phrase("vat",font)));
        table.addCell(new PdfPCell(new Phrase("wartosc",font)));
        for(Car car : list){
            table.addCell(new PdfPCell(new Phrase(licznik + "",font)));
            table.addCell(new PdfPCell(new Phrase(car.getRok(),font)));
            table.addCell(new PdfPCell(new Phrase(car.getCena()+"",font)));
            table.addCell(new PdfPCell(new Phrase(car.getVat() + "%",font)));
            table.addCell(new PdfPCell(new Phrase(invoice.CalcVat(car)+"",font)));
            licznik+=1;
        }
        document.add(table);
        font = FontFactory.getFont(FontFactory.COURIER_BOLD, 16, BaseColor.BLACK);

        document.add(new Chunk("DO ZAPLATY " + invoice.SumPrice() + " PLN", font));
        document.add(p);

        document.close();
        return invoice.getTitle();
    }

    public static String GeneratePriceCarsInvoice(ArrayList<Car> list,String min,String max) throws FileNotFoundException, DocumentException {
        Invoice invoice = new Invoice("firma sprzedajaca auta","nabywca",list);

        Document document = new Document(); // dokument pdf
        String path = "invoices/invoice_price_cars_"+invoice.getTitle()+".pdf";
        PdfWriter.getInstance(document, new FileOutputStream(path));

        document.open();
        generateInvoiceModel(invoice.getTitle(), invoice.getSeller(), invoice.getBuyer(),"Faktura za auta w cenach: " + min + "-" + max +" PLN" ,document );

        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Paragraph p = new Paragraph("", font);
        int licznik = 1;
        PdfPTable table = new PdfPTable(5);
        table.addCell(new PdfPCell(new Phrase("lp",font)));
        table.addCell(new PdfPCell(new Phrase("rok",font)));
        table.addCell(new PdfPCell(new Phrase("cena",font)));
        table.addCell(new PdfPCell(new Phrase("vat",font)));
        table.addCell(new PdfPCell(new Phrase("wartosc",font)));
        for(Car car : list){
            table.addCell(new PdfPCell(new Phrase(licznik + "",font)));
            table.addCell(new PdfPCell(new Phrase(car.getRok(),font)));
            table.addCell(new PdfPCell(new Phrase(car.getCena()+"",font)));
            table.addCell(new PdfPCell(new Phrase(car.getVat() + "%",font)));
            table.addCell(new PdfPCell(new Phrase(invoice.CalcVat(car)+"",font)));
            licznik+=1;
        }
        document.add(table);
        font = FontFactory.getFont(FontFactory.COURIER_BOLD, 16, BaseColor.BLACK);

        document.add(new Chunk("DO ZAPLATY " + invoice.SumPrice() + " PLN", font));
        document.add(p);

        document.close();
        return invoice.getTitle();
    }

    private static void generateInvoiceModel(String title, String seller,String buyer,String filter,Document document) throws DocumentException {
        Font font = FontFactory.getFont(FontFactory.COURIER_BOLD, 16, BaseColor.BLACK);
        Paragraph p = new Paragraph("", font);

        document.add(new Chunk("FAKTURA: " + title, font));
        document.add(p);
        font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        document.add(new Chunk("Sprzedawca: " + seller, font));
        document.add(p);
        document.add(new Chunk("Nabywca: " + buyer, font));
        document.add(p);
        font = FontFactory.getFont(FontFactory.COURIER_BOLD, 16, BaseColor.RED);
        document.add(new Chunk(filter, font));
        document.add(p);
    }

}
