package edu.umg.plataforma.auth;

import lombok.*;
import edu.umg.plataforma.util.RolEnum;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RespuestaAuthenticacion {
    private String token;
    private Integer idUsuario;
    private String nombre;
    private String apellido;
    private String email;
    private String contrasenia;
    private RolEnum rol;
}