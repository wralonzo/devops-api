package edu.umg.plataforma.controller;

import edu.umg.plataforma.auth.RespuestaAuthenticacion;
import edu.umg.plataforma.auth.SolicitudAuthenticacion;
import edu.umg.plataforma.auth.SolicitudRegistro;
import edu.umg.plataforma.service.AuthenticacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticacionService AuthenticacionService;

    @PostMapping("registro")
    public ResponseEntity<RespuestaAuthenticacion> registro(@RequestBody SolicitudRegistro solicitudRegistro) {
        return ResponseEntity.ok(AuthenticacionService.regsitro(solicitudRegistro));
    }

    @PostMapping("authenticar")
    public ResponseEntity<RespuestaAuthenticacion> authenticar(@RequestBody SolicitudAuthenticacion solicitudAuthenticacion) {
        return ResponseEntity.ok(AuthenticacionService.authenticar(solicitudAuthenticacion));
    }
}
