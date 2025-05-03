package it.epicode.eventi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventoRequest {

    private String titolo;
    private String descrizione;
    private String luogo;
    private String data;
    private int numeroPosti;
}
