package edu.umg.plataforma.service;

import edu.umg.plataforma.auth.RespuestaAuthenticacion;
import edu.umg.plataforma.auth.SolicitudAuthenticacion;
import edu.umg.plataforma.auth.SolicitudRegistro;

public interface AuthenticacionService {
    RespuestaAuthenticacion regsitro(SolicitudRegistro solicitudRegistro);

    RespuestaAuthenticacion authenticar(SolicitudAuthenticacion solicitudAuthenticacion);
}
