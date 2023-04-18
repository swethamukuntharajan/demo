package com.example.demo.dao;
import java.util.*;
import com.example.demo.model.Transaction;
import java.sql.*;
import java.lang.*;
import java.math.*;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;

@Component
public class WalletDAO {
    private static final String INSERT_SQL = "INSERT INTO transactions(DateTime, Amount) VALUES (?, ?)";
    private static final String SELECT_BY_HOUR_SQL = "SELECT DateTime, Amount AS Amount FROM transactions WHERE DateTime >= ? AND DateTime < ? ";


    @Autowired
private DataSource dataSource;

    public void saveRecord(BigDecimal amount) throws SQLException {
        
        try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(INSERT_SQL)) {
            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setBigDecimal(2, amount);
            stmt.executeUpdate();
    stmt.close();
        }
    }

    public List<Transaction> getBalanceHistory(LocalDateTime startDatetime, LocalDateTime endDatetime) throws SQLException {
        List<Transaction> balances = new ArrayList<>();

        try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(SELECT_BY_HOUR_SQL)) {
            stmt.setTimestamp(1, Timestamp.valueOf(startDatetime));
            stmt.setTimestamp(2, Timestamp.valueOf(endDatetime));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                LocalDateTime datetime = rs.getTimestamp("datetime").toLocalDateTime();
                BigDecimal balance = new BigDecimal(rs.getDouble("amount"));
                balances.add(new Transaction(datetime, balance));
            }
            rs.close();
    stmt.close();
        }

        return balances;
    }


}
