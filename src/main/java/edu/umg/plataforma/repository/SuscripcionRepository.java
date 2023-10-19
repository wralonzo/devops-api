package edu.umg.plataforma.repository;

import edu.umg.plataforma.entity.SuscripcionEntity;
import edu.umg.plataforma.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuscripcionRepository extends JpaRepository<SuscripcionEntity, Integer> {
    SuscripcionEntity findByUsuarioAndIdSuscripcion(UsuarioEntity usuario, Integer idSuscripcion);
}
