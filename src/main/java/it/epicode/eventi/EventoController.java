package it.epicode.eventi;

import it.epicode.auth.AppUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventi")
@RequiredArgsConstructor
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ORGANIZER')")
    @ResponseStatus(HttpStatus.CREATED)
    public Evento createEvento(@RequestBody @Valid EventoRequest evento, @AuthenticationPrincipal AppUser organizerLoggato) {
        return eventoService.createEvent(evento, organizerLoggato);

    }

    @GetMapping
    public List<Evento> findAll() {
        return eventoService.findAll();
    }

    @GetMapping("/{id}")
    public Evento findById(@PathVariable Long id) {
        return eventoService.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ORGANIZER')")
    public Evento updateEvento(@PathVariable Long id, @RequestBody EventoRequest evento, @AuthenticationPrincipal AppUser organizerLoggato) {
        return eventoService.updateEvento(id, evento, organizerLoggato);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ORGANIZER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvento(@PathVariable Long id, @AuthenticationPrincipal AppUser organizerLoggato) {
        eventoService.deleteEvento(id, organizerLoggato);
    }
}
