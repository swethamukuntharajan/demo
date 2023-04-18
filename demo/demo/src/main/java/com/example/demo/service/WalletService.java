package com.example.demo.service;
import java.util.*;
import java.lang.*;
import java.sql.SQLException;
import com.example.demo.dao.WalletDAO;
import com.example.demo.model.Transaction;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class WalletService {
    
    @Autowired
    private final WalletDAO dao;

    public WalletService(WalletDAO dao) {
        this.dao = dao;
    }

    public void saveRecord(BigDecimal amount) throws SQLException {
        dao.saveRecord(amount);
    }

    public List<Transaction> getBalanceHistory(LocalDateTime startDatetime, LocalDateTime endDatetime) throws SQLException {
        if (startDatetime.isAfter(endDatetime)) {
            throw new IllegalArgumentException("startDatetime must be before endDatetime");
        }

        return dao.getBalanceHistory(startDatetime, endDatetime);
    }

    public String sumAmountByHour(List<Transaction> balances) {
    // Group the objects by the hour of the datetime field
    Map<String, List<Transaction>> groupedByHour = balances.stream()
        .collect(Collectors.groupingBy(o -> o.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))));

    // Sum the amount for each hour
    //Map<String, BigDecimal> result = new LinkedHashMap<>();
    //for (Map.Entry<String, List<Balance>> entry : groupedByHour.entrySet()) {
       // Balance bal;
        //BigDecimal sum = entry.getValue().stream()
            //.map(bal.getBalance())
            //.sum();
        //result.put(entry.getKey(), sum);
   // }
   Map<LocalDateTime, Double> hourlySums = balances.stream()
    .collect(Collectors.groupingBy(
        balance -> balance.getDateTime().truncatedTo(ChronoUnit.HOURS),
        Collectors.summingDouble(balance -> balance.getAmount().doubleValue())
    ));
    String json="";
    try{
    ObjectMapper objectMapper = new ObjectMapper();
         json= objectMapper.writeValueAsString(hourlySums);
    }
    catch (JsonProcessingException e) {
     e.printStackTrace();
    }
    return json;
}
}
