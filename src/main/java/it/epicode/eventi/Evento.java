package it.epicode.eventi;

import it.epicode.auth.AppUser;
import jakarta.persistence.*;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "eventi")
@Entity
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, length = 100)
    private String titolo;
    @Column(nullable = false, length = 200)
    private String descrizione;
    @Column(nullable = false, length = 50)
    private String luogo;
    @Column(nullable = false)
    private LocalDate data;
    @Column(nullable = false)
    private int numeroPosti;
    @Column(nullable = false)
    private int postiPrenotati;
    @ManyToOne
    @JoinColumn(name = "organizer_id", nullable = false)
    private AppUser organizer;
}
