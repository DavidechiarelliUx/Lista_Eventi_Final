package it.epicode.eventi;

import it.epicode.auth.AppUser;
import it.epicode.auth.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    public Evento createEvent(EventoRequest eventoReq, AppUser organizerLoggato) {

        Evento evento = new Evento();
        evento.setTitolo(eventoReq.getTitolo());
        evento.setDescrizione(eventoReq.getDescrizione());
        evento.setData(LocalDate.parse(eventoReq.getData()));
        evento.setLuogo(eventoReq.getLuogo());
        evento.setNumeroPosti(eventoReq.getNumeroPosti());
        evento.setPostiPrenotati(0);
        evento.setOrganizer(organizerLoggato);

        return eventoRepository.save(evento);
    }

    public Evento getEventoById(Long id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento non trovato"));
    }

    public List<Evento> findAll() {
        return eventoRepository.findAll();
    }

    public Evento findById(Long id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento non trovato"));
    }

    public Evento updateEvento(Long id, EventoRequest eventoReq, AppUser organizerLoggato) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento non trovato"));

        boolean isOrganizer = organizerLoggato.getRoles().contains(Role.ROLE_ORGANIZER);
        if (evento.getOrganizer().getId() == organizerLoggato.getId() || isOrganizer) {
            evento.setTitolo(eventoReq.getTitolo());
            evento.setDescrizione(eventoReq.getDescrizione());
            evento.setData(LocalDate.parse(eventoReq.getData()));
            evento.setLuogo(eventoReq.getLuogo());
            evento.setNumeroPosti(eventoReq.getNumeroPosti());
            return eventoRepository.save(evento);
        }else {
            throw new RuntimeException("Non sei l'organizzatore dell'evento");
        }
    }
    public void deleteEvento(Long id, AppUser organizerLoggato) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento non trovato"));

        boolean isOrganizer = organizerLoggato.getRoles().contains(Role.ROLE_ORGANIZER);
        if (evento.getOrganizer().getId() == organizerLoggato.getId() || isOrganizer) {
            eventoRepository.delete(evento);
        }else {
            throw new RuntimeException("Non sei l'organizzatore dell'evento");
        }
    }


}
