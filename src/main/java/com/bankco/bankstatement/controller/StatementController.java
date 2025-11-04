// package com.bankco.bankstatement.controller;

// import com.bankco.bankstatement.domain.customer.Customer;
// import com.bankco.bankstatement.domain.customer.CustomerRepository;
// import com.bankco.bankstatement.domain.transaction.Transaction;
// import com.bankco.bankstatement.domain.transaction.TransactionRepository;
// import com.bankco.bankstatement.service.PdfGeneratorService;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/statements")
// public class StatementController {

//     private final CustomerRepository customerRepository;
//     private final TransactionRepository transactionRepository;
//     private final PdfGeneratorService pdfGeneratorService;

//     public StatementController(CustomerRepository customerRepository,
//                                TransactionRepository transactionRepository,
//                                PdfGeneratorService pdfGeneratorService) {
//         this.customerRepository = customerRepository;
//         this.transactionRepository = transactionRepository;
//         this.pdfGeneratorService = pdfGeneratorService;
//     }

//     @GetMapping("/{customerId}/pdf")
//     public ResponseEntity<String> generateStatementPdf(@PathVariable Long customerId) {
//         try {
//             Customer customer = customerRepository.findById(customerId)
//                     .orElseThrow(() -> new RuntimeException("Customer not found"));

//             List<Transaction> transactions = transactionRepository.findByCustomerId(customerId);

//             String pdfBytes = pdfGeneratorService.generateStatementPdf(customer, transactions);

//             HttpHeaders headers = new HttpHeaders();
//             headers.setContentType(MediaType.APPLICATION_PDF);
//             headers.setContentDispositionFormData("attachment", customer.getName() + "_statement.pdf");

//             return ResponseEntity.ok()
//                     .headers(headers)
//                     .body(pdfBytes);

//         } catch (Exception e) {
//             e.printStackTrace();
//             return ResponseEntity.internalServerError().build();
//         }
//     }
// }
