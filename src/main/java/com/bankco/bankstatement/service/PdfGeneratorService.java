package com.bankco.bankstatement.service;

import com.bankco.bankstatement.domain.customer.Customer;
import com.bankco.bankstatement.domain.transaction.Transaction;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PdfGeneratorService {

    @Autowired
    private Configuration freemarkerConfig;

    public String generateStatementPdf(Customer customer, List<Transaction> transactions)
            throws IOException, TemplateException {

        // 1️⃣ Create data model for template
        Map<String, Object> model = new HashMap<>();
        model.put("customer", customer);
        model.put("transactions", transactions);

        // 2️⃣ Load Freemarker template
        Template template = freemarkerConfig.getTemplate("statement-template.ftl");
        String htmlContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

        // 3️⃣ Define structured folder path
        LocalDate now = LocalDate.now();
        String baseDir = "statements";
        String year = String.valueOf(now.getYear());
        String month = String.format("%02d", now.getMonthValue());
        String safeName = customer.getName().replaceAll("[^a-zA-Z0-9]", "_"); // handle special chars

        Path folderPath = Path.of(baseDir, year, month);
        Files.createDirectories(folderPath);

        String fileName = safeName + ".pdf";
        Path filePath = folderPath.resolve(fileName);

        // 4️⃣ Generate PDF from HTML
        try (OutputStream os = new FileOutputStream(filePath.toFile())) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(htmlContent, null);
            builder.toStream(os);
            builder.run();
        }

        return filePath.toAbsolutePath().toString();
    }
}
