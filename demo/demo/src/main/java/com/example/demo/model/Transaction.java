package com.example.demo.model;
import java.util.*;
import java.lang.*;
import java.time.LocalDateTime;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "transactions")
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "DateTime", nullable = false)
    private LocalDateTime datetime;
    
    @Column(name = "Amount", nullable = false)
    private BigDecimal amount;
    
    public Transaction(LocalDateTime datetime, BigDecimal amount) {
        this.datetime = datetime;
        this.amount = amount;
    }
    // constructors, getters, setters, and other methods
    public LocalDateTime getDateTime() {
        return datetime;
    }

    public BigDecimal getAmount() {
        return amount;
    }
     public void setDateTime(LocalDateTime datetime) {
        this.datetime= datetime;
    }

    public void setAmount( BigDecimal amount) {
        this.amount= amount;
    }
}
