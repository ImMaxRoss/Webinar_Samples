package models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Expense {
    private int id;
    private BigDecimal amount;
    private String category;
    private String description;
    private LocalDateTime date;

    public Expense(int id, BigDecimal amount, String category, String description, LocalDateTime date) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Expense [id=" + id + ", amount=" + amount + ", category=" + category + ", description=" + description
                + ", date=" + date + "]";
    }

    
    

}
