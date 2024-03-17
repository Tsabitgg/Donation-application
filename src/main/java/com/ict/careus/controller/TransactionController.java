package com.ict.careus.controller;

import com.ict.careus.dto.request.TransactionRequest;
import com.ict.careus.dto.response.TransactionResponse;
import com.ict.careus.model.transaction.Transaction;
import com.ict.careus.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/{transactionType}/{code}")
    public ResponseEntity<TransactionResponse> createTransaction(@PathVariable("transactionType") String transactionType,
                                                                 @PathVariable("code") String code,
                                                                 @RequestBody TransactionRequest transactionRequest) {
        Transaction createdTransaction = transactionService.createTransaction(transactionType, code, transactionRequest);

        // Mapping Transaction to TransactionResponse
        TransactionResponse transactionResponse = mapTransactionToResponse(createdTransaction);

        return ResponseEntity.ok().body(transactionResponse);
    }

    // Helper method to map Transaction to TransactionResponse
    private TransactionResponse mapTransactionToResponse(Transaction transaction) {
        TransactionResponse response = new TransactionResponse();
        response.setTransactionId(transaction.getTransactionId());
        response.setUsername(transaction.getUsername());
        response.setPhoneNumber(transaction.getPhoneNumber());
        response.setTransactionAmount(transaction.getTransactionAmount());
        response.setMessage(transaction.getMessage());
        response.setTransactionDate(transaction.getTransactionDate());
        response.setSuccess(transaction.isSuccess());
        return response;
    }
}

