package com.example.todolistapp.entity;

import com.example.todolistapp.enums.Categories;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "Task")
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", columnDefinition = "VARCHAR(255) default 'PENDING'")
    private Categories category = Categories.PENDING;

    @Basic
    private LocalDateTime createdAt;

    @Basic
    private LocalDateTime updatedAt;

    @Basic
    private LocalDateTime completedAt;

    @ManyToOne()
    @JoinColumn(name = "user_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "task_user_id_fk")
    )
    private User user;

    public Task(String title, String description, Categories category, User user) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.user = user;
    }

//    @PrePersist
//    public void setCreatedAt(){
//        createdAt = new Date();
//    }
//    @PreUpdate
//    public void setUpdatedAt(){
//        updatedAt= new Date();
//    }
//    @PostUpdate
//    public void setCompletedAt(){
//        updatedAt= new Date();
//    }
}
