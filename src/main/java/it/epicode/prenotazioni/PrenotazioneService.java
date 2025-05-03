package it.epicode.prenotazioni;

import it.epicode.auth.AppUser;
import it.epicode.auth.Role;
import it.epicode.eventi.Evento;
import it.epicode.eventi.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private EventoRepository eventoRepository;


    public Prenotazione createPrenotazione(Long eventoId, int postiPrenotati, AppUser utenteLoggato) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento non trovato"+ eventoId));

        //verifica posti disponibili :
        int totPrenotati = prenotazioneRepository
                .findByEventoId(eventoId)
                .stream()
                .mapToInt(Prenotazione::getPostiPrenotati)
                .sum();
        int disponibilita = evento.getNumeroPosti() - totPrenotati;
        System.out.println("Sono rimasti solo " + disponibilita + " posti");
        if (postiPrenotati > disponibilita) {
            throw new RuntimeException("Sono rimasti solo " + disponibilita + " posti");
        }
        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setEvento(evento);
        prenotazione.setUtenteLoggato(utenteLoggato);
        prenotazione.setPostiPrenotati(postiPrenotati);

        evento.setPostiDisponibili(disponibilita - postiPrenotati);

        eventoRepository.save(evento);

        return prenotazioneRepository.save(prenotazione);

    }

    public void deletePrenotazione(Long id, AppUser utenteLoggato) {
        Prenotazione prenotazione = prenotazioneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prenotazione non trovata"));

        boolean isOrganizer = utenteLoggato.getRoles().contains(Role.ROLE_ORGANIZER);
        if (prenotazione.getUtenteLoggato().getId() == utenteLoggato.getId() || isOrganizer) {
            prenotazioneRepository.delete(prenotazione);
        }else {
            throw new RuntimeException("Non sei l'utente che ha prenotato l'evento");
        }
    }
    public List<Prenotazione> getPrenotazioniByEventoId(Long eventoId) {
        return prenotazioneRepository.findByEventoId(eventoId);
    }
    public List<Prenotazione> getPrenotazioniByUtenteId(Long utenteId) {
        return prenotazioneRepository.findByUtenteLoggatoId(utenteId);
    }


}
