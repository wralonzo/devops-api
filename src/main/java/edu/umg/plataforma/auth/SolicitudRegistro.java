package edu.umg.plataforma.auth;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudRegistro {
    private String nombre;

    private String apellido;

    private String email;

    private String contrasenia;
}
