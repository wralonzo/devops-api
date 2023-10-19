package edu.umg.plataforma.service;

import edu.umg.plataforma.entity.SuscripcionEntity;
import edu.umg.plataforma.repository.SuscripcionRepository;
import edu.umg.plataforma.util.RolEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SuscripcionServiceImpl implements SuscripcionService {
    private final SuscripcionRepository suscripcionRepository;
    private final HttpServletRequest httpServletRequest;
    private final JwtService jwtService;

    @Override
    @Transactional(readOnly = true)
    public List<SuscripcionEntity> listadoSuscripciones() {
        var jwtToken = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        var usuarioLogeado = jwtService.getUsuario(jwtToken);
        if (usuarioLogeado.getRol() == RolEnum.DIRECTOR_SALUD ||
                usuarioLogeado.getRol() == RolEnum.ENCARGADO_CAPACITACION ||
                usuarioLogeado.getRol() == RolEnum.AUXILAR) {
            return suscripcionRepository.findAll();
        } else {
            throw new AccessDeniedException("El usuario no tiene permiso para ver la lista de suscripciones");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public SuscripcionEntity encontrarSuscripcion(Integer idSuscripcion) {
        var jwtToken = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        var usuarioLogeado = jwtService.getUsuario(jwtToken);
        if (usuarioLogeado.getRol() == RolEnum.DIRECTOR_SALUD ||
                usuarioLogeado.getRol() == RolEnum.ENCARGADO_CAPACITACION ||
                usuarioLogeado.getRol() == RolEnum.AUXILAR) {
            return suscripcionRepository.findById(idSuscripcion).orElseThrow(null);
        } else {
            throw new AccessDeniedException("El usuario no tiene permiso para buscar una suscripcion");
        }
    }

    @Override
    @Transactional
    public SuscripcionEntity crearSuscripcion(@Valid @RequestBody SuscripcionEntity suscripcionEntity) {
        var jwtToken = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        var usuarioLogeado = jwtService.getUsuario(jwtToken);
        if (usuarioLogeado.getRol() == RolEnum.CLIENTE) {
            suscripcionEntity.setUsuario(usuarioLogeado);
            return suscripcionRepository.save(suscripcionEntity);
        } else {
            throw new AccessDeniedException("El usuario no tiene permiso para crear una suscripcion");
        }
    }

    @Override
    @Transactional
    public SuscripcionEntity modificarSuscripcion(SuscripcionEntity suscripcionEntity) {
        var jwtToken = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        var usuarioLogeado = jwtService.getUsuario(jwtToken);
        if (suscripcionEntity == null) {
            throw new AccessDeniedException("No se encontró ninguna suscripción para el usuario logueado");
        }
        suscripcionEntity.setUsuario(usuarioLogeado);
        return suscripcionRepository.save(suscripcionEntity);
    }

    @Override
    @Transactional
    public void eliminarSuscripcion(Integer idSuscripcion) {
        var jwtToken = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        var usuarioLogeado = jwtService.getUsuario(jwtToken);
        if (usuarioLogeado.getRol() == RolEnum.DIRECTOR_SALUD) {
            Optional<SuscripcionEntity> suscripcionExistente = suscripcionRepository.findById(idSuscripcion);
            if (suscripcionExistente.isPresent()) {
                suscripcionRepository.delete(suscripcionExistente.get());
            } else {
                throw new NoSuchElementException("La suscripcion no se encuentra.");
            }
        } else {
            throw new AccessDeniedException("El usuario no tiene permiso para eliminar una suscripcion");
        }
    }
}
