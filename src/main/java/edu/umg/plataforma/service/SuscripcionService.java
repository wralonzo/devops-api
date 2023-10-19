package edu.umg.plataforma.service;

import edu.umg.plataforma.entity.SuscripcionEntity;

import java.util.List;

public interface SuscripcionService {
    List<SuscripcionEntity> listadoSuscripciones();

    SuscripcionEntity encontrarSuscripcion(Integer idSuscripcion);

    SuscripcionEntity crearSuscripcion(SuscripcionEntity suscripcionEntity);

    SuscripcionEntity modificarSuscripcion(SuscripcionEntity suscripcionEntity);

    void eliminarSuscripcion(Integer idSuscripcion);
}
