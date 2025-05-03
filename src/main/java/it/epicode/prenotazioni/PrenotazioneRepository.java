package it.epicode.prenotazioni;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    List<Prenotazione> findByEventoId(Long eventoId);
    List<Prenotazione> findByUtenteLoggatoId(Long utenteId);
}
