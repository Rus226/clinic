package ru.clinic.backend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Data
@NoArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String secondName;

    private String patronymic;

    @Pattern(regexp="[\\d]{10}", message = "Only 10 digits")
    private String phoneNumber;

    public String getFirstAndSecondName(){
        return firstName + " " + secondName;
    }
}
