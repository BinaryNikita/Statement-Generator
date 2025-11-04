package com.bankco.bankstatement.service;

import com.bankco.bankstatement.domain.customer.Customer;
import com.bankco.bankstatement.domain.customer.CustomerRepository;
import com.bankco.bankstatement.domain.transaction.Transaction;
import com.bankco.bankstatement.domain.transaction.TransactionRepository;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatementService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    /**
     * Generate PDF for a single customer and return the generated file path.
     * Used by Spring Batch processor (multi-threaded).
     */

     public void generateStatementForCustomer(Customer customer) {
    List<Transaction> transactions = transactionRepository.findByCustomerId(customer.getId());
    try {
        pdfGeneratorService.generateStatementPdf(customer, transactions);
        System.out.println("✅ PDF generated for customer: " + customer.getName());
    } catch (IOException | TemplateException e) {
        System.err.println("❌ Failed for " + customer.getName() + ": " + e.getMessage());
    }
}

    public String generateStatementForCustomerAndReturnPath(Customer customer) throws IOException, TemplateException {
        List<Transaction> transactions = transactionRepository.findByCustomerId(customer.getId());
        return pdfGeneratorService.generateStatementPdf(customer, transactions);
    }

    /**
     * Generate PDFs for all customers (used by REST API/manual trigger).
     */
    public Map<String, String> generateAllStatements() {
        Map<String, String> results = new LinkedHashMap<>();

List<Customer> customers = (List<Customer>) customerRepository.findAll(Sort.unsorted());

        for (Customer customer : customers) {
            try {
                String filePath = generateStatementForCustomerAndReturnPath(customer);
                results.put(customer.getName(), "✅ PDF generated: " + filePath);
            } catch (IOException | TemplateException e) {
                results.put(customer.getName(), "❌ Failed to generate PDF: " + e.getMessage());
            }
        }

        return results;
    }
}
