package it.epicode.prenotazioni;

import it.epicode.auth.AppUser;
import it.epicode.eventi.EventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prenotazioni")
@RequiredArgsConstructor
public class PrenotazioneController {
    @Autowired
    private PrenotazioneService prenotazioneService;

    @Autowired
    private EventoService eventoService;

    @PostMapping("/create/{eventoId}")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazione createPrenotazione(@PathVariable Long eventoId, @RequestParam int postiPrenotati, @AuthenticationPrincipal AppUser utenteLoggato) {
        return prenotazioneService.createPrenotazione(eventoId, postiPrenotati, utenteLoggato);
    }

    @GetMapping("/evento/{eventoId}")
    public List<Prenotazione> getByEvento(@PathVariable Long eventoId) {
        return prenotazioneService.getPrenotazioniByEventoId(eventoId);
    }

    @GetMapping("/utente")
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<Prenotazione> getByUtente(@AuthenticationPrincipal AppUser utenteLoggato) {
        return prenotazioneService.getPrenotazioniByUtenteId(utenteLoggato.getId());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ORGANIZER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePrenotazione(
            @PathVariable Long id,
            @AuthenticationPrincipal AppUser utenteLoggato
    ) {
        prenotazioneService.deletePrenotazione(id, utenteLoggato);
    }



}
