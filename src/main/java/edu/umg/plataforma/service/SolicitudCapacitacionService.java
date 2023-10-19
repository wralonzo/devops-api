package edu.umg.plataforma.service;

import edu.umg.plataforma.entity.SolicitudCapacitacionEntity;

import java.util.List;

public interface SolicitudCapacitacionService {
    List<SolicitudCapacitacionEntity> listadoSolicitudesCapacitacion();

    SolicitudCapacitacionEntity encontrarSolicitudCapacitacion(Integer idSolicitudCapacitacion);

    SolicitudCapacitacionEntity crearSolicitudCapacitacion(SolicitudCapacitacionEntity solicitudCapacitacion);

    SolicitudCapacitacionEntity modificarSolicitudCapacitacion(SolicitudCapacitacionEntity solicitudCapacitacion);

    void eliminarSolicitudCapacitacion(Integer idSolicitudCapacitacion);
}
