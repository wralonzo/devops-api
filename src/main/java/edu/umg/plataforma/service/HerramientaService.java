package edu.umg.plataforma.service;

import edu.umg.plataforma.entity.HerramientaEntity;

import java.util.List;

public interface HerramientaService {
    List<HerramientaEntity> listadoHerramientas();

    HerramientaEntity encontrarHerramienta(Integer idHerramienta);

    HerramientaEntity crearHerramienta(HerramientaEntity herramientaEntity);

    HerramientaEntity modificarHerramienta(HerramientaEntity herramientaEntity);

    void eliminarHerramienta(Integer idHerramienta);
}
