package com.example.godel.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate localDate;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "category_id", referencedColumnName = "categoryId")
    private Category category;

    @NotNull
    private Double amount;

    public Expense(String name, LocalDate localDate, Double amount) {
        this.name = name;
        this.localDate = localDate;
        this.amount = amount;
    }
}
