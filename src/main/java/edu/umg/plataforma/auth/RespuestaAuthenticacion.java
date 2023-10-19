package edu.umg.plataforma.auth;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RespuestaAuthenticacion {
    private String token;
}
