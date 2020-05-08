package ru.ruslan.backend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank(message = "Description can not be empty")
    @Column(length = 2000)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "patient_id")
    @NotNull
    private Patient patient;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "doctor_id")
    @NotNull
    private Doctor doctor;

    @CreationTimestamp
//    @Column(updatable = false)
//    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateCreation;

    @NotNull
//    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateTermination;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Priority priority;
}
