package com.example.storage_manager.service;

import com.example.storage_manager.entity.Category;
import com.example.storage_manager.entity.Product;
import com.example.storage_manager.entity.Sex;
import com.example.storage_manager.repository.ProductRepository;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.itextpdf.layout.Document;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final ProductRepository productRepository;

    public ReportService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        List<Product> products = productRepository.findAll();

        // Totais por categoria
        Map<Category, Long>categoryTotals = products.stream()
                .collect(Collectors.groupingBy(Product::getCategory, Collectors.counting()));
        stats.put("categoryTotals", categoryTotals);

        // Totais por sexo
        Map<Sex, Long> sexoTotals = products.stream()
                .collect(Collectors.groupingBy(Product::getSex, Collectors.counting()));
        stats.put("sexoTotals", sexoTotals);

        // Total de produtos com alerta
        long alertCount = products.stream()
                .filter(Product::isPendingAlert)
                .count();
        stats.put("alertCount", alertCount);

        return stats;
    }

    public byte[] generateCsvReport() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Relatório de Produtos");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Nome");
        header.createCell(2).setCellValue("Categoria");
        header.createCell(3).setCellValue("Sexo");
        header.createCell(4).setCellValue("Quantidade");
        header.createCell(5).setCellValue("Alerta Pendente");

        List<Product> products = productRepository.findAll();
        for (int i = 0; i < products.size(); i++) {
            Row row = sheet.createRow(i + 1);
            Product product = products.get(i);
            row.createCell(0).setCellValue(product.getId());
            row.createCell(1).setCellValue(product.getName());
            row.createCell(2).setCellValue(product.getCategory().toString());
            row.createCell(3).setCellValue(product.getSex().toString());
            row.createCell(4).setCellValue(product.getQuantity());
            row.createCell(5).setCellValue(product.isPendingAlert() ? "Sim" : "Não");
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            workbook.write(out);
            workbook.close();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar CSV", e);
        }
        return out.toByteArray();
    }

    public byte[] generatePdfReport() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new com.itextpdf.layout.Document(pdf);
            document.add(new Paragraph("Relatório de Produtos").setBold().setFontSize(14));

            List<Product> products = productRepository.findAll();
            for (Product product : products) {
                document.add(new Paragraph(
                        String.format("ID: %d, Nome: %s, Categoria: %s, Sexo: %s, Quantidade: %d, Alerta Pendente: %s",
                                product.getId(), product.getName(), product.getCategory(), product.getSex(),
                                product.getQuantity(), product.isPendingAlert() ? "Sim" : "Não")
                ));
            }

            document.close();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF", e);
        }
        return out.toByteArray();
    }
}