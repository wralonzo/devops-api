package edu.umg.plataforma.service;

import edu.umg.plataforma.entity.CapacitacionEntity;

import java.util.List;

public interface CapacitacionService {
    List<CapacitacionEntity> listadoCapacitaciones();

    CapacitacionEntity encontrarCapacitacion(Integer idCapacitacion);

    CapacitacionEntity crearCapacitacion(CapacitacionEntity capacitacionEntity);

    CapacitacionEntity modificarCapacitacion(CapacitacionEntity capacitacionEntity);

    void eliminarCapacitacion(Integer idCapacitacion);
}
