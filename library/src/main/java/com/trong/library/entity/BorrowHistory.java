package com.trong.library.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
public class BorrowHistory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;
    private boolean returned;
}
