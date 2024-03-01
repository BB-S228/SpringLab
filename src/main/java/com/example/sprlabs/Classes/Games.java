package com.example.sprlabs.Classes;

import jakarta.validation.constraints.NotEmpty;
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
@Table("games")
public class Games {
    @Id
    private long id;
    @Min(value = 1, message = "Выберите разработчика!")
    private long dev_id;
    @Size(min = 3, message = "Поле не должно быть пустым!")
    private String name;
    @NotEmpty(message = "Поле не может быть пустым!")
    private String release_year;
    @Size(min = 3, message = "Поле не должно быть пустым!")
    private String genre;
    @Size(min = 3, message = "Поле не должно быть пустым!")
    private String system;
    @NotNull
    private Developers dev;

    public Games(long dev_id, String name, String release_year, String genre, String system, Developers dev) {
        this.dev_id = dev_id;
        this.name = name;
        this.release_year = release_year;
        this.genre = genre;
        this.system = system;
        this.dev = dev;
    }
}
