package com.example.sprlabs.Classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("developers")
public class Developers {
    @Id
    private long id;
    @Size(min = 3, message = "Поле не может быть пустым!")
    private String name;
    @Min(value = 1, message = "Поле не может быть пустым!")
    private float rating;

    public Developers(String name, float rating) {
        this.name = name;
        this.rating = rating;
    }
}
