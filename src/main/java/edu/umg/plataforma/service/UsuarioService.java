package edu.umg.plataforma.service;

import edu.umg.plataforma.entity.UsuarioEntity;

import java.util.List;

public interface UsuarioService {
    List<UsuarioEntity> listadoUsuarios();

    UsuarioEntity encontrarUsuario(Integer idUsuario);

    UsuarioEntity crearUsuario(UsuarioEntity usuarioEntity);

    UsuarioEntity modificarUsuario(UsuarioEntity usuarioEntity);

    void eliminarUsuario(Integer idUsuario);
}
