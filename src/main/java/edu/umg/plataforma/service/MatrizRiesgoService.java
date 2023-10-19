package edu.umg.plataforma.service;

import edu.umg.plataforma.entity.MatrizRiesgoEntity;

import java.util.List;

public interface MatrizRiesgoService {
    List<MatrizRiesgoEntity> listadoMatrizesRiesgo();

    MatrizRiesgoEntity encontrarMatrizRiesgo(Integer idMatrizRiesgo);

    MatrizRiesgoEntity crearMatrizRiesgo(MatrizRiesgoEntity matrizRiesgoEntity);

    MatrizRiesgoEntity modificarMatrizRiesgo(MatrizRiesgoEntity matrizRiesgoEntity);

    void eliminarMatrizRiesgo(Integer idMatrizRiesgo);
}
