package com.example.todolistapp.pojo;

import com.example.todolistapp.enums.Categories;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TaskDto {


    private Long id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String description;
    private Categories category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime completedAt;

    public TaskDto(String title,
                   String description,
                   Categories category,
                   LocalDateTime createdAt,
                   LocalDateTime updatedAt,
                   LocalDateTime completedAt){
        this.title = title;
        this.description = description;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.completedAt = completedAt;
    }
}
