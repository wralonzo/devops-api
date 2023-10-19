package edu.umg.plataforma.service;

import edu.umg.plataforma.entity.UsuarioEntity;
import edu.umg.plataforma.repository.UsuarioRepository;
import edu.umg.plataforma.util.RolEnum;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final HttpServletRequest httpServletRequest;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioEntity> listadoUsuarios() {
        var jwtToken = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        var usuarioLogeado = jwtService.getUsuario(jwtToken);
        if (usuarioLogeado.getRol() == RolEnum.DIRECTOR_SALUD ||
                usuarioLogeado.getRol() == RolEnum.ENCARGADO_CAPACITACION ||
                usuarioLogeado.getRol() == RolEnum.AUXILAR) {
            return usuarioRepository.findAll();
        } else {
            throw new AccessDeniedException("El usuario no tiene permiso para ver la lista de usuarios");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioEntity encontrarUsuario(Integer idUsuario) {
        var jwtToken = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        var usuarioLogeado = jwtService.getUsuario(jwtToken);
        if (usuarioLogeado.getRol() == RolEnum.DIRECTOR_SALUD ||
                usuarioLogeado.getRol() == RolEnum.ENCARGADO_CAPACITACION ||
                usuarioLogeado.getRol() == RolEnum.AUXILAR) {
            Optional<UsuarioEntity> usuario = usuarioRepository.findById(idUsuario);
            return usuario.orElseThrow(() -> new NoSuchElementException("El usuario no se encuentra."));
        } else {
            throw new AccessDeniedException("El usuario no tiene permiso para buscar al usuario");
        }
    }

    @Override
    @Transactional
    public UsuarioEntity crearUsuario(UsuarioEntity usuarioEntity) {
        var jwtToken = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        var usuarioLogeado = jwtService.getUsuario(jwtToken);
        if (usuarioLogeado.getRol() == RolEnum.DIRECTOR_SALUD) {
            usuarioEntity.setContrasenia(passwordEncoder.encode(usuarioEntity.getContrasenia()));
            usuarioRepository.save(usuarioEntity);
            return usuarioEntity;
        } else {
            throw new AccessDeniedException("El usuarioEntity no tiene permiso para crear usuarios");
        }
    }

    @Override
    @Transactional
    public UsuarioEntity modificarUsuario(UsuarioEntity usuarioEntity) {
        var jwtToken = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        var usuarioLogeado = jwtService.getUsuario(jwtToken);
        if (usuarioLogeado.getRol() == RolEnum.DIRECTOR_SALUD) {
            usuarioEntity.setContrasenia(passwordEncoder.encode(usuarioEntity.getContrasenia()));
            usuarioRepository.save(usuarioEntity);
            return usuarioEntity;
        } else {
            throw new AccessDeniedException("El usuarioEntity no tiene permiso para modificar usuarios");
        }
    }

    @Override
    @Transactional
    public void eliminarUsuario(Integer idUsuario) {
        var jwtToken = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        var usuarioLogeado = jwtService.getUsuario(jwtToken);
        if (usuarioLogeado.getRol() == RolEnum.DIRECTOR_SALUD) {
            Optional<UsuarioEntity> usuarioExistente = usuarioRepository.findById(idUsuario);
            if (usuarioExistente.isPresent()) {
                usuarioRepository.delete(usuarioExistente.get());
            } else {
                throw new NoSuchElementException("El usuario no se encuentra.");
            }
        } else {
            throw new AccessDeniedException("El usuario no tiene permiso para eliminar usuarios");
        }
    }
}
