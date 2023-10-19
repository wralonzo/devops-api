package edu.umg.plataforma.auth;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudAuthenticacion {
    private String email;

    private String contrasenia;
}
