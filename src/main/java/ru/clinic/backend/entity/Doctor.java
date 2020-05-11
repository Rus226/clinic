package ru.clinic.backend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String secondName;

    private String patronymic;

    @NotBlank
    private String specialization;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.EAGER)
    private List<Recipe> recipes;

    public String getFirstAndSecondName(){
        return firstName + " " + secondName;
    }
}
