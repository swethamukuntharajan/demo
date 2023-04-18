package com.example.demo.controller;
import java.util.*;
import com.example.demo.service.WalletService;
import com.example.demo.model.DateTimeRange;
import com.example.demo.model.Transaction;
import java.sql.*;
import java.sql.SQLException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.*;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@RestController
public class WalletController {
    private final WalletService service;

    public WalletController(WalletService service) {
        this.service = service;
    }

    @PostMapping("/records/{amount}")
    public ResponseEntity<Void> saveRecord(@PathVariable BigDecimal amount) {
        try {
            service.saveRecord(amount);
            return ResponseEntity.ok().build();
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/balances")
    public String getBalanceHistory(@RequestBody DateTimeRange request) {
        String json ="";
        try {
            List<Transaction> balances = service.getBalanceHistory(request.getStartDatetime(), request.getEndDatetime());
        json= service.sumAmountByHour(balances);
    }
     catch (SQLException e) {
             e.printStackTrace();
        }
           return json;
    }
}
