package edu.umg.plataforma.service;

import edu.umg.plataforma.entity.AsignacionHerramientaEntity;

import java.util.List;

public interface AsignacionHerramientaService {
    List<AsignacionHerramientaEntity> listadoAsignacionesHerramientas();

    AsignacionHerramientaEntity encontrarAsignacionHerramienta(Integer idAsignacionHerramienta);

    AsignacionHerramientaEntity crearAsignacionHerramienta(AsignacionHerramientaEntity asignacionHerramientaEntity);

    AsignacionHerramientaEntity modificarAsignacionHerramienta(AsignacionHerramientaEntity asignacionHerramientaEntity);

    void eliminarAsignacionHerramienta(Integer idAsignacionHerramienta);
}
